package com.thoughtful.notegenie.admin.type;

public enum PaymentStatus {
    PENDING,
    COMPLETED,
    FAILED,
    CANCELLED,
    REFUNDED,
    ERROR;

    public static PaymentStatus from(String status) {
        try {
            return PaymentStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid payment status: " + status);
        }
    }
}
