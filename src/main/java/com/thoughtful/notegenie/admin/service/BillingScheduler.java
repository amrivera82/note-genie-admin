package com.thoughtful.notegenie.admin.service;

import com.thoughtful.notegenie.admin.data.model.Customer;
import com.thoughtful.notegenie.admin.data.model.Invoice;
import com.thoughtful.notegenie.admin.data.model.Payment;
import com.thoughtful.notegenie.admin.data.model.Product;
import com.thoughtful.notegenie.admin.data.model.Subscription;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BillingScheduler {

    private final BillingService billingService;
    private final CustomerService customerService;

    @Scheduled(cron = "0 0 1 1/1 * ?")
    public void runMonthlyInvoiceAndChargeCycle() {
        processMonthlyBilling();
        processBulkPayments();
    }

    @Transactional(dontRollbackOn = Exception.class) // test whether this will cover all exceptions
    public void processMonthlyBilling() {
        try {
            // Get active customers
            final List<Customer> activeCustomers = customerService.getActiveCustomers();

            // Generate invoices
            activeCustomers.forEach(customer -> {
                final List<Product> products = customer.getActiveSubscriptions()
                        .stream()
                        .map(Subscription::getProduct)
                        .toList();
                billingService.generateInvoice(customer.getId(), products);
            });

            log.info("Monthly billing cycle completed successfully");
        } catch (Exception e) {
            log.error("Error processing monthly billing", e);
            throw new BillingProcessingException("Failed to process monthly billing", e);
        }
    }

    public void processBulkPayments() {
        try {
            // What's the general difference between a DRAFT and CREATED invoice?
            final List<Invoice> pendingPayments = billingService.getPendingPayments();

            if (!pendingPayments.isEmpty()) {
                pendingPayments.forEach(invoice -> {
                    try {
                        log.info("Creating payment for customer: {}", invoice.getCustomer());
                        billingService.processPayment(invoice, new Payment());
                    } catch (final Exception e) {
                        log.error("Encountered error while attempting to process payment for customer {}",
                                invoice.getCustomer().getId(), e);
                    }
                });
            }

            log.info("Bulk payment processing completed; attempted to processing {} pending payments.", pendingPayments.size());
        } catch (Exception e) {
            log.error("Error processing bulk payments", e);
            throw new BillingProcessingException("Failed to process bulk payments", e);
        }
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public static class BillingProcessingException extends RuntimeException {
        public BillingProcessingException(String message) {
            super(message);
        }

        public BillingProcessingException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

