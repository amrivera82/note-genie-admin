package com.thoughtful.notegenie.admin.data.repository;

import com.thoughtful.notegenie.admin.data.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByEmail(String email);

    List<Customer> findBySuspended(boolean suspended);
}
