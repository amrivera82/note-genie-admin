package com.thoughtful.notegenie.admin.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "payment_token")
public class PaymentToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "square_customer_id")
    private String squareCustomerId;

    @Column(name = "customer_id")
    private Long noteGenieCustomerId;

    @Column(name = "payment_token")
    private String paymentToken;

    @Column(name = "status_code")
    private String statusCode;

    // todo: add converter
    @Column(name = "created_timestamp")
    private LocalDateTime createdDateTime;

    // todo: add converter
    @Column(name = "edited_timestamp")
    private LocalDateTime editedDateTime;

}
