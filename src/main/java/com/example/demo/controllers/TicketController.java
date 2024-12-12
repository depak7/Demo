package com.example.demo.controllers;


import com.example.demo.dto.TicketDto;
import com.example.demo.models.Ticket;
import com.example.demo.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("add")
    public ResponseEntity<TicketDto> addTicket(@RequestBody TicketDto ticket) {
        ticketService.AddTicket(ticket);
        return new ResponseEntity<>(ticket, HttpStatus.CREATED);
    }


}
