package com.thoughtful.notegenie.admin.controller.type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Supports only Square card tokens
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private Long invoiceId;
    private Long customerId;
    private BigDecimal amount;
    private String idempotencyKey;
}
