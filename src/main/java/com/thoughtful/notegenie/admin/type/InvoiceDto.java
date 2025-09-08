package com.thoughtful.notegenie.admin.type;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceDto {
    private Long id;
    private String customerId;
    private BigDecimal totalAmount;
    private InvoiceStatus status;
    private String paymentId;
}
