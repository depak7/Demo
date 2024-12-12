package com.example.demo.controllers;

import com.example.demo.dto.TicketRequestDto;
import com.example.demo.dto.TicketResponseDto;
import com.example.demo.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getAllTicketsForClient(@PathVariable int clientId,
                                                    @RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "10") int size) {
        return ticketService.getAllTicketsForClient(clientId, page, size);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getTicketById(@PathVariable int id) {
        return ticketService.getTicketById(id);
    }


    @PostMapping
    public ResponseEntity<?> addOrUpdateTicket(@RequestBody TicketRequestDto ticketRequestDto) {
        return ticketService.addOrUpdateTicket(ticketRequestDto);
    }
}
