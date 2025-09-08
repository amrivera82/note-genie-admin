package com.thoughtful.notegenie.admin.controller;

import com.thoughtful.notegenie.admin.data.model.Invoice;
import com.thoughtful.notegenie.admin.data.model.Product;
import com.thoughtful.notegenie.admin.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/billing")
public class BillingController {

    private final BillingService billingService;

    @PostMapping("/invoices")
    public ResponseEntity<Invoice> generateInvoice(
            @PathVariable Long customerId,
            @RequestBody List<Product> products) {
        Invoice invoice = billingService.generateInvoice(customerId, products);
        return ResponseEntity.ok(invoice);
    }

    @GetMapping("/invoices/{customerId}")
    public ResponseEntity<List<Invoice>> getCustomerInvoices(
            @PathVariable Long customerId) {
        List<Invoice> invoices = billingService.getCustomerInvoices(customerId);
        return ResponseEntity.ok(invoices);
    }
}
