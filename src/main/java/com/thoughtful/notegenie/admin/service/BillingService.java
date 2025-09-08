package com.thoughtful.notegenie.admin.service;

import com.squareup.square.types.Payment;
import com.thoughtful.notegenie.admin.controller.type.PaymentRequest;
import com.thoughtful.notegenie.admin.data.model.Customer;
import com.thoughtful.notegenie.admin.data.model.Invoice;
import com.thoughtful.notegenie.admin.data.model.InvoiceItem;
import com.thoughtful.notegenie.admin.data.model.Product;
import com.thoughtful.notegenie.admin.data.repository.InvoiceRepository;
import com.thoughtful.notegenie.admin.data.repository.PaymentRepository;
import com.thoughtful.notegenie.admin.type.InvoiceStatus;
import com.thoughtful.notegenie.admin.type.PaymentStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BillingService {

    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentService paymentService;
    private final CustomerService customerService;

    @Transactional
    public Invoice generateInvoice(Long customerId, List<Product> products) {
        try {
            final Customer customer = customerService.getCustomer(customerId);
            validateCustomer(customer);
            final Invoice invoice = Invoice.builder()
                    .customer(customer)
                    .status(InvoiceStatus.CREATED)
                    .totalAmount(calculateTotal(products))
                    .build();

            final List<InvoiceItem> invoiceItems = products.stream()
                    .map(product -> InvoiceItem.builder()
                            .price(product.getPrice())
                            .total(product.getPrice())
                            .product(product)
                            .invoice(invoice)
                            .quantity(1)
                            .build()).toList();

            invoice.setItems(invoiceItems);

            return invoiceRepository.save(invoice);
        } catch (Exception e) {
            log.error("Error generating invoice", e);
            throw new BillingException("Failed to generate invoice", e);
        }
    }

    @Transactional(dontRollbackOn = Exception.class)
    public void processPayment(final Invoice invoice, final com.thoughtful.notegenie.admin.data.model.Payment ourPayment) {
        try {
            final PaymentRequest paymentRequest = PaymentRequest.builder()
                    .invoiceId(invoice.getId())
                    .amount(invoice.getTotalAmount())
                    .customerId(invoice.getCustomer().getId())
                    .idempotencyKey(UUID.randomUUID().toString())
                    .build();

            // Create payment at Square
            final Payment payment = paymentService.createPayment(paymentRequest)
                    .orElseThrow(() -> new RuntimeException("No payment returned for request [" + paymentRequest + "]"));

            payment.getId().ifPresentOrElse(id -> {
                invoice.setPaymentId(id);
                ourPayment.setPaymentId(id);
            }, () -> {
                log.warn("Payment ID not found in response! Payment info provided: {}", payment);
                ourPayment.setPaymentId("ERROR");
                invoice.setPaymentId("ERROR");
            });

            // !! Handle reconciliation of sent and processed amounts ** //

            payment.getStatus().ifPresentOrElse(status -> {
                log.debug("payment {} was *{}*", status, payment.getId());
                switch (status) {
                    // review and revise logic according to actual process context
                    // and general best practices
                    case "APPROVED" -> {
                        ourPayment.setStatus(PaymentStatus.PENDING);
                        invoice.setStatus(InvoiceStatus.SENT);
                    }
                    case "CANCELED" -> {
                        ourPayment.setStatus(PaymentStatus.CANCELLED);
                        invoice.setStatus(InvoiceStatus.CANCELLED);
                    }
                    case "COMPLETED" -> {
                        ourPayment.setStatus(PaymentStatus.COMPLETED);
                        invoice.setStatus(InvoiceStatus.PAID);
                    }
                    case "FAILED" -> {
                        ourPayment.setStatus(PaymentStatus.FAILED);
                        invoice.setStatus(InvoiceStatus.CANCELLED);
                    }
                    default -> {
                        log.error("Unexpected value: {}", status);
                        ourPayment.setStatus(PaymentStatus.ERROR);
                    }
                }
            }, () -> {
                log.debug("No payment status was provided for created payment;" +
                        " excepted one of [APPROVED, PENDING, COMPLETED, CANCELED, or FAILED for version ?]");
                ourPayment.setStatus(PaymentStatus.ERROR);
                invoice.setStatus(InvoiceStatus.ERROR);
            });

            ourPayment.setCreatedDateTime(LocalDateTime.now());
            paymentRepository.saveAndFlush(ourPayment);
            invoiceRepository.saveAndFlush(invoice);
        } catch (final Exception e) {
            log.error("Error processing payment", e);
            throw new BillingException("Failed to process payment", e);
        }
    }

    public List<Invoice> getPendingPayments() {
        return invoiceRepository.findByStatusIn(List.of(InvoiceStatus.CREATED, InvoiceStatus.OVERDUE));
    }

    public List<Invoice> getCustomerInvoices(Long customerId) {
        return invoiceRepository.findByCustomerId(customerId);
    }

    private void validateCustomer(Customer customer) {
        if (customer == null) {
            throw new CustomerService.CustomerNotFoundException("Customer not found");
        }
        if (customer.isSuspended()) {
            throw new BillingException("Customer account is suspended");
        }
    }

    private BigDecimal calculateTotal(List<Product> products) {
        return products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class BillingException extends RuntimeException {
        public BillingException(String message) {
            super(message);
        }

        public BillingException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

