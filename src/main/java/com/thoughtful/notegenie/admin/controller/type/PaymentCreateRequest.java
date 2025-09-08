package com.thoughtful.notegenie.admin.controller.type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCreateRequest {
    private BigDecimal amount;
    private String currency;
    private String paymentMethodId;
    private String idempotencyKey;
}
