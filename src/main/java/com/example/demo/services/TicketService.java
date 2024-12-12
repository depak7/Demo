package com.example.demo.services;

import com.example.demo.dto.TicketRequestDto;
import com.example.demo.dto.TicketResponseDto;
import com.example.demo.models.Ticket;
import com.example.demo.repo.ClientRepository;
import com.example.demo.repo.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ClientRepository clientRepository;

    // Fetch all tickets for a client with pagination
    public ResponseEntity<Page<TicketResponseDto>> getAllTicketsForClient(int clientId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Ticket> ticketPage = ticketRepository.findByClientId(clientId, pageable);
        Page<TicketResponseDto> ticketResponseDtoPage = ticketPage.map(this::mapToTicketResponseDto);
        return new ResponseEntity<>(ticketResponseDtoPage, HttpStatus.OK);
    }

    // Fetch a single ticket by ID
    public ResponseEntity<TicketResponseDto> getTicketById(long id) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        if (ticketOptional.isEmpty()) {
            throw new TicketNotFoundException(id);
        }
        Ticket ticket = ticketOptional.get();
        TicketResponseDto ticketResponseDto = mapToTicketResponseDto(ticket);
        return new ResponseEntity<>(ticketResponseDto, HttpStatus.OK);
    }

    // Add or Update a ticket
    public ResponseEntity<TicketResponseDto> addOrUpdateTicket(TicketRequestDto ticketRequestDto) {
        if (!clientRepository.existsById(ticketRequestDto.getClientId())) {
            throw new ClientNotFoundException(ticketRequestDto.getClientId());
        }

        Ticket ticket = new Ticket();


        ticket.setClient(clientRepository.findById(ticketRequestDto.getClientId()).get());
        String generatedTicketCode = UUID.randomUUID().toString();
        ticket.setTicketCode(generatedTicketCode);
        ticket.setTitle(ticketRequestDto.getTitle());
        ticket.setStatus(ticketRequestDto.getStatus());
        ticket.setLastModifiedDate(new Date());

        Ticket savedTicket = ticketRepository.save(ticket);
        TicketResponseDto ticketResponseDto = mapToTicketResponseDto(savedTicket);

        return new ResponseEntity<>(ticketResponseDto, HttpStatus.CREATED);
    }


    private TicketResponseDto mapToTicketResponseDto(Ticket ticket) {
        TicketResponseDto ticketResponseDto = new TicketResponseDto();
        ticketResponseDto.setId(ticket.getId());
        ticketResponseDto.setTicketCode(ticket.getTicketCode());
        ticketResponseDto.setTitle(ticket.getTitle());
        ticketResponseDto.setStatus(ticket.getStatus());
        ticketResponseDto.setLastModifiedDate(ticket.getLastModifiedDate());
        return ticketResponseDto;
    }


    public static class TicketNotFoundException extends RuntimeException {
        public TicketNotFoundException(long id) {
            super("Ticket with id " + id + " not found");
        }
    }

    public static class ClientNotFoundException extends RuntimeException {
        public ClientNotFoundException(long clientId) {
            super("Client with id " + clientId + " not found");
        }
    }
}
