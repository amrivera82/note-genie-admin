package com.thoughtful.notegenie.admin.service;

import com.thoughtful.notegenie.admin.data.model.Customer;
import com.thoughtful.notegenie.admin.data.model.Invoice;
import com.thoughtful.notegenie.admin.data.model.PaymentToken;
import com.thoughtful.notegenie.admin.data.repository.CustomerRepository;
import com.thoughtful.notegenie.admin.type.CustomerDto;
import com.thoughtful.notegenie.admin.type.SubscriptionStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PaymentService paymentService;

    public Customer createCustomer(CustomerDto customerDto) {
        final Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());

        // Create default payment token
        final PaymentToken paymentToken = new PaymentToken();
        paymentToken.setSquareCustomerId(customer.getId().toString());
        paymentToken.setPaymentToken(generateSecureToken());
        paymentToken.setEditedDateTime(LocalDateTime.now());

        paymentService.updatePaymentToken(customer.getId().toString(),
                paymentToken.getPaymentToken());

        return customerRepository.save(customer);
    }

    public Customer getCustomer(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
    }

    public List<Customer> getActiveCustomers() {
        return customerRepository.findBySuspended(false)
                .stream()
                .filter(this::hasActiveSubscriptions)
                .collect(Collectors.toList());
    }

    private boolean hasActiveSubscriptions(Customer customer) {
        return customer.getSubscriptions().stream()
                .anyMatch(subscription ->
                        subscription.getStatus().equals(SubscriptionStatus.ACTIVE));
    }

    public Customer updatePaymentMethod(Long customerId, String paymentToken) {
        final Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        final PaymentToken paymentTokenEntity = new PaymentToken();
        paymentTokenEntity.setSquareCustomerId(customer.getId().toString());
        paymentTokenEntity.setPaymentToken(paymentToken);
        paymentTokenEntity.setEditedDateTime(LocalDateTime.now());

        paymentService.updatePaymentToken(customer.getId().toString(),
                paymentToken);

        return customer;
    }

    public void deactivateAccount(Long customerId) {
        final Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        customer.setSuspended(true);
        customerRepository.save(customer);
    }

    private String generateSecureToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public List<Invoice> getCustomerInvoices(Long customerId) {
        return customerRepository.findById(customerId)
                .map(Customer::getInvoices)
                .orElse(Collections.emptyList());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class CustomerNotFoundException extends RuntimeException {
        public CustomerNotFoundException(String message) {
            super(message);
        }
    }
}
