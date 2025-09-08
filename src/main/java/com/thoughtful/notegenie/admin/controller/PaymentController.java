package com.thoughtful.notegenie.admin.controller;

import com.squareup.square.types.Payment;
import com.thoughtful.notegenie.admin.controller.type.PaymentRequest;
import com.thoughtful.notegenie.admin.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<Payment> processPayment(
            @RequestBody PaymentRequest request) throws Exception {
        Payment payment = paymentService.createPayment(request).orElse(null);
        return ResponseEntity.ok(payment);
    }

    @PutMapping("/update-token/{customerId}")
    public ResponseEntity<Void> updatePaymentToken(
            @PathVariable Long customerId,
            @RequestBody String paymentToken) {
        paymentService.updatePaymentToken(customerId.toString(), paymentToken);
        return ResponseEntity.ok().build();
    }
}
