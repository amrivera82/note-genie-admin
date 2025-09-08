package com.thoughtful.notegenie.admin.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "status_code")
    private String statusCode;

    // todo: add converter
    @Column(name = "created_timestamp")
    private LocalDateTime createdDateTime;

    // todo: add converter
    @Column(name = "edited_timestamp")
    private LocalDateTime editedDateTime;

}
