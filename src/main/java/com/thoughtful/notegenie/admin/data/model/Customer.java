package com.thoughtful.notegenie.admin.data.model;

import com.thoughtful.notegenie.admin.type.SubscriptionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.type.YesNoConverter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email_address")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address_line_a")
    private Object addressLineA;

    @Column(name = "address_line_b")
    private String addressLineB;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zipcode")
    private String zipCode;

    @Column(name = "is_suspended")
    @Convert(converter = YesNoConverter.class)
    private boolean isSuspended;

    @OneToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    @OneToMany(mappedBy = "")
    private List<Subscription> subscriptions;

    @OneToMany(mappedBy = "customer")
    private List<Invoice> invoices;

    // todo: add converter
    @Column(name = "created_timestamp")
    private ZonedDateTime createdDateTime;

    // todo: add converter
    @Column(name = "edited_timestamp")
    private ZonedDateTime editedDateTime;

    public List<Subscription> getActiveSubscriptions() {
        return this.subscriptions.stream()
                .filter(sub -> sub.getStatusCode().equals(SubscriptionStatus.ACTIVE))
                .toList();
    }
}
