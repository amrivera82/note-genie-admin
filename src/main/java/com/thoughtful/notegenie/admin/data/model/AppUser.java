package com.thoughtful.notegenie.admin.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne(mappedBy = "user_id")
    private Customer customer;

    @Column(name = "created_timestamp")
    private ZonedDateTime createdDateTime;

    @Column(name = "edited_timestamp")
    private ZonedDateTime editedDateTime;

    @Column(name = "status_code")
    private String statusCode;

    @Column(name = "zone_id")
    private String zoneId;
}
