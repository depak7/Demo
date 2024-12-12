package com.example.demo.controllers;


import com.example.demo.dto.ClientRequestDto;
import com.example.demo.dto.ClientResponseDto;
import com.example.demo.dto.ErrorResponse;
import com.example.demo.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/client")
public class ClientController {
    @Autowired
    private ClientService   clientService;

    @PostMapping("create")
    public ResponseEntity<ClientResponseDto> createClient(@RequestBody ClientRequestDto client) {
        return clientService.addClient(client);

    }

    @GetMapping("{id}")
    public ResponseEntity<ClientResponseDto> getAllClients(@PathVariable Long id) {
        return clientService.getClient(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<ClientResponseDto> updateClient(@PathVariable Long id, @RequestBody ClientRequestDto client) {
        return clientService.updateClient(id,client);
    }


    @ExceptionHandler({
            ClientService.UserNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(Exception e) {
        String message;
        HttpStatus status;
        if(e instanceof ClientService.UserNotFoundException) {
            message = e.getMessage();
            status = HttpStatus.NOT_FOUND;
        }
        else {
            message = "Something went wrong";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        ErrorResponse response=ErrorResponse.builder()
                        .message(message)
                        .build();


        return  ResponseEntity.status(status).body(response);


    }
}
