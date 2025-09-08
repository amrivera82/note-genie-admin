package com.thoughtful.notegenie.admin.data.repository;

import com.thoughtful.notegenie.admin.data.model.PaymentToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentTokenRepository extends JpaRepository<PaymentToken, Long> {
    Optional<PaymentToken> findByCustomerId(Long noteGenieCustomerId);
}
