package com.thoughtful.notegenie.admin.service;

import com.squareup.square.SquareClient;
import com.squareup.square.core.SquareApiException;
import com.squareup.square.types.CreatePaymentRequest;
import com.squareup.square.types.CreatePaymentResponse;
import com.squareup.square.types.Currency;
import com.squareup.square.types.CustomerDetails;
import com.squareup.square.types.Money;
import com.squareup.square.types.Payment;
import com.thoughtful.notegenie.admin.controller.type.PaymentRequest;
import com.thoughtful.notegenie.admin.data.model.PaymentToken;
import com.thoughtful.notegenie.admin.data.repository.PaymentTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    private final SquareClient squareClient;
    private final PaymentTokenRepository paymentTokenRepository;

    public Optional<Payment> createPayment(final PaymentRequest request) throws Exception {
        // verify internal customerId always corresponding to exactly 1 Square customer ID (which implemented elsewhere)
        final Long customerId = request.getCustomerId();
        try {
            final PaymentToken token = getPaymentToken(customerId);
            final CreatePaymentResponse response = squareClient.payments()
                    .create(CreatePaymentRequest.builder()
                            .sourceId("CARD")
                            .idempotencyKey(request.getIdempotencyKey())
                            .amountMoney(Money.builder()
                                    .amount(request.getAmount().longValue())
                                    .currency(Currency.USD)
                                    .build())
                            .customerId(token.getSquareCustomerId())
                            .verificationToken(token.getPaymentToken())
                            .customerDetails(CustomerDetails.builder()
                                    .customerInitiated(false)
                                    .sellerKeyedIn(true) // todo: confirm need/value
                                    .build())
                            .build());

            return response.getPayment();
        } catch (SquareApiException e) {
            //408 (Timeout)
            //429 (Too Many Requests)
            //5XX (Internal Server Errors)
            log.error("Square payment creation failed", e);
            throw e;
        } catch (final Exception e) {
            log.debug(e.getMessage(), e);
            throw e;
        }
    }

    public void updatePaymentToken(String customerId, String paymentToken) {
        final PaymentToken token = PaymentToken.builder()
                .squareCustomerId(customerId)
                .paymentToken(paymentToken)
                .createdDateTime(LocalDateTime.now())
                .build();

        paymentTokenRepository.save(token);
    }

    public PaymentToken getPaymentToken(Long customerId) {
        return paymentTokenRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new PaymentTokenNotFoundException("Payment token not found"));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public static class PaymentProcessingException extends RuntimeException {
        public PaymentProcessingException(String message) {
            super(message);
        }

        public PaymentProcessingException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class PaymentTokenNotFoundException extends RuntimeException {
        public PaymentTokenNotFoundException(String message) {
            super(message);
        }
    }

}