package com.thoughtful.notegenie.admin.data.model;

import com.thoughtful.notegenie.admin.type.InvoiceStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Column(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "invoice")
    @Column(name = "item")
    private List<InvoiceItem> items;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "status_code")
    @Enumerated(EnumType.STRING)
    private InvoiceStatus statusCode;

    @Column(name = "payment_id")
    private String paymentId;

    // todo: add converter
    @Column(name = "created_timestamp")
    private LocalDateTime createdDateTime;

    // todo: add converter
    @Column(name = "edited_timestamp")
    private LocalDateTime editedDateTime;

}
