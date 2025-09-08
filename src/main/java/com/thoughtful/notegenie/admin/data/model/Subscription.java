package com.thoughtful.notegenie.admin.data.model;

import com.thoughtful.notegenie.admin.type.SubscriptionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "status_code")
    @Enumerated(value = EnumType.STRING)
    private SubscriptionStatus statusCode;

    @Column(name = "next_billing_date")
    private LocalDateTime nextBillingDate;

    // todo: add converter
    @Column(name = "created_timestamp")
    private LocalDateTime createdDateTime;

    // todo: add converter
    @Column(name = "edited_timestamp")
    private LocalDateTime editedDateTime;

}


