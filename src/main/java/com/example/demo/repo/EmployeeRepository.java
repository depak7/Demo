package com.example.demo.repo;

import com.example.demo.models.Employees;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository  extends JpaRepository<Employees,Integer> {

    Page<Employees> findByClientId(Long clientId, Pageable pageable);

    Page<Employees> findByDesignation(String designation, Pageable pageable);
}
