package com.example.demo.services;


import com.example.demo.dto.ClientRequestDto;
import com.example.demo.dto.ClientResponseDto;
import com.example.demo.dto.Mapper;
import com.example.demo.models.Client;
import com.example.demo.repo.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public ResponseEntity<ClientResponseDto> getClient(long id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (!clientOptional.isPresent()) {
            throw new UserNotFoundException(id);
        }
        Client client = clientOptional.get();
        ClientResponseDto clientResponseDTO = Mapper.toClientDto(client);
        return new ResponseEntity<>(clientResponseDTO, HttpStatus.OK);
    }

    public ResponseEntity<ClientResponseDto> addClient(ClientRequestDto client) {
        Client newClient = new Client();
        newClient.setEnabled(client.isEnabled());
        newClient.setClientCode(client.getClientCode());
        newClient.setCreatedDate(new Date());
        newClient.setLastModifiedDate(new Date());
        clientRepository.save(newClient);
        ClientResponseDto clientResponseDTO = Mapper.toClientDto(newClient);
        return new ResponseEntity<>(clientResponseDTO, HttpStatus.CREATED);

    }

    public ResponseEntity<ClientResponseDto> updateClient(long id,ClientRequestDto clientRequestDto) {
        Optional<Client> clientData = clientRepository.findById(id);
        if (!clientData.isPresent()) {
            throw new UserNotFoundException(id);
        }
        Client existingClient = clientData.get();
        existingClient.setClientCode(clientRequestDto.getClientCode());
        existingClient.setClientName(clientRequestDto.getClientName());
        existingClient.setEnabled(clientRequestDto.isEnabled());
        existingClient.setLastModifiedDate(new Date());
        Client updatedClient = clientRepository.save(existingClient);
        ClientResponseDto updatedClientResponseDTO = Mapper.toClientDto(updatedClient);
        return new ResponseEntity<>(updatedClientResponseDTO, HttpStatus.OK);
    }

    public static class UserNotFoundException extends IllegalArgumentException{
        public UserNotFoundException(long id) {
            super("Client with id " + id + " not found");
        }
    }


}
