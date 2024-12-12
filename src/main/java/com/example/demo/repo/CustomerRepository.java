package com.example.demo.repo;

import com.example.demo.models.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Page<Customer> findByClientId(Long clientId, Pageable pageable);

    Optional<Customer> findByClientIdAndCustomerCode(Long clientId, String customerCode);
}
