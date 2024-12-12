package com.example.demo.services;


import com.example.demo.dto.Mapper;
import com.example.demo.dto.TicketDto;
import com.example.demo.models.Ticket;
import com.example.demo.repo.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public TicketDto AddTicket(TicketDto ticketDto) {
        Ticket ticket = Mapper.ticketEntity(ticketDto);
        ticketRepository.save(ticket);
        return ticketDto;
    }


}

