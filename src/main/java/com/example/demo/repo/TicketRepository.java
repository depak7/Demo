package com.example.demo.repo;

import com.example.demo.models.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Page<Ticket> findByClientId(int clientId, Pageable pageable);
}
