package com.thoughtful.notegenie.admin.type;

public enum InvoiceStatus {
    CREATED,
    SENT,
    PAID,
    OVERDUE,
    CANCELLED,
    DRAFT,
    ERROR;

    public static InvoiceStatus fromString(String status) {
        try {
            return InvoiceStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid invoice status: " + status);
        }
    }
}
