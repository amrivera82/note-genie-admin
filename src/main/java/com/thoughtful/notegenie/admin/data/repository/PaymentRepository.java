package com.thoughtful.notegenie.admin.data.repository;

import com.thoughtful.notegenie.admin.data.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
