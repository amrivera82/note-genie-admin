package com.thoughtful.notegenie.admin.type;

public enum SubscriptionStatus {
    ACTIVE,
    CANCELLED,
    EXPIRED,
    PAST_DUE;

    public static SubscriptionStatus from(String status) {
        try {
            return SubscriptionStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid subscription status: " + status);
        }
    }
}
