package com.example.demo.ExceptionHandler;

import com.example.demo.services.ClientService;
import com.example.demo.services.CustomerService;
import com.example.demo.services.EmployeeService;
import com.example.demo.services.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.demo.dto.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DisasterHandler {

    Logger logger = LoggerFactory.getLogger(DisasterHandler.class);

    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(Exception e) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .message(e.getMessage())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception e) {
        if (e instanceof ClientService.UserNotFoundException) {
            return handleResourceNotFoundException(e);
        } else if (e instanceof CustomerService.ClientNotFoundException) {
            return handleResourceNotFoundException(e);
        } else if (e instanceof CustomerService.CustomerNotFoundException) {
            return handleResourceNotFoundException(e);
        } else if (e instanceof EmployeeService.EmployeeNotFoundException) {
            return handleResourceNotFoundException(e);
        } else if (e instanceof EmployeeService.ClientNotFoundException) {
            return handleResourceNotFoundException(e);
        }
        logger.error("Root cause: ", e);
        return new ResponseEntity<>(ErrorResponse.builder()
                .message("An unexpected error occurred.")
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

