package com.example.demo.controllers;

import com.example.demo.dto.CustomerRequestDto;
import com.example.demo.dto.CustomerResponseDto;
import com.example.demo.dto.ErrorResponse;
import com.example.demo.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getAllCustomersForClient(@PathVariable Long clientId, @RequestParam int page, @RequestParam int size) {
        System.out.println(clientId);
        if (page < 0 || size <= 0) {
            return new ResponseEntity<>("Invalid page or size parameters", HttpStatus.BAD_REQUEST);
        }
        return customerService.getAllCustomersForClient(clientId, page, size);
    }

    @PostMapping("create")
    public ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody CustomerRequestDto customerRequestDto) {
        return customerService.addCustomer(customerRequestDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable Long id) {
        System.out.println(id);
        return customerService.getCustomerById(id);
    }

    @PostMapping("")
    public ResponseEntity<CustomerResponseDto> addCustomer(@RequestBody CustomerRequestDto customerRequestDto) {
        return customerService.addCustomer(customerRequestDto);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@PathVariable Long id, @RequestBody CustomerRequestDto customerRequestDto) {
        return customerService.updateCustomer(id, customerRequestDto);
    }

    @ExceptionHandler({
            CustomerService.CustomerNotFoundException.class,
            CustomerService.ClientNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(Exception e) {
        String message;
        HttpStatus status;
        System.out.println(e);
        if(e instanceof  CustomerService.CustomerNotFoundException) {
            message = e.getMessage();
            status = HttpStatus.NOT_FOUND;
        }
        else if(e instanceof  CustomerService.ClientNotFoundException) {
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
