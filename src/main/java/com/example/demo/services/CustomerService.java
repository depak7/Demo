package com.example.demo.services;

import com.example.demo.dto.CustomerRequestDto;
import com.example.demo.dto.CustomerResponseDto;
import com.example.demo.dto.Mapper;
import com.example.demo.models.Customer;
import com.example.demo.repo.ClientRepository;
import com.example.demo.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ClientRepository clientRepository;

    public ResponseEntity<Page<CustomerResponseDto>> getAllCustomersForClient(Long clientId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customerPage = customerRepository.findByClientId(clientId, pageable);

        Page<CustomerResponseDto> customerResponseDtoPage = customerPage.map(Mapper::toCustomerDto);
        return new ResponseEntity<>(customerResponseDtoPage, HttpStatus.OK);
    }

    public ResponseEntity<CustomerResponseDto> getCustomerById(Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (!customerOptional.isPresent()) {
            throw new CustomerNotFoundException(id);
        }

        Customer customer = customerOptional.get();
        CustomerResponseDto customerResponseDTO = Mapper.toCustomerDto(customer);
        return new ResponseEntity<>(customerResponseDTO, HttpStatus.OK);
    }

    private String generateCustomerCode(Long clientId, String email) {
        String emailPart = email.split("@")[0];
        return "C" + clientId + "-" + emailPart;
    }

    public ResponseEntity<CustomerResponseDto> addCustomer(CustomerRequestDto customerRequestDto) {
        if (!clientRepository.existsById(customerRequestDto.getClientId())) {
            throw new ClientNotFoundException(customerRequestDto.getClientId());
        }

        String generatedCustomerCode = generateCustomerCode(customerRequestDto.getClientId(), customerRequestDto.getEmail());
        Customer customer = Mapper.toCustomerEntity(customerRequestDto, clientRepository);
        customer.setCustomerCode(generatedCustomerCode);
        customer.setCreatedDate(new Date());
        customer.setLastModifiedDate(new Date());
        Customer savedCustomer = customerRepository.save(customer);
        CustomerResponseDto savedCustomerResponseDto = Mapper.toCustomerDto(savedCustomer);
        return new ResponseEntity<>(savedCustomerResponseDto, HttpStatus.CREATED);
    }

    public ResponseEntity<CustomerResponseDto> updateCustomer(Long id, CustomerRequestDto customerRequestDto) {
        Optional<Customer> existingCustomerOptional = customerRepository.findById(id);
        if (!existingCustomerOptional.isPresent()) {
            throw new CustomerNotFoundException(id);
        }

        Customer existingCustomer = existingCustomerOptional.get();
        existingCustomer.setCustomerName(customerRequestDto.getCustomerName());
        existingCustomer.setEmail(customerRequestDto.getEmail());
        existingCustomer.setPhoneNumber(customerRequestDto.getPhoneNumber());
        existingCustomer.setEnabled(customerRequestDto.isEnabled());
        existingCustomer.setLastModifiedDate(new Date());
        Customer updatedCustomer = customerRepository.save(existingCustomer);

        CustomerResponseDto updatedCustomerResponseDto = Mapper.toCustomerDto(updatedCustomer);
        return new ResponseEntity<>(updatedCustomerResponseDto, HttpStatus.OK);
    }


    public static class CustomerNotFoundException extends IllegalArgumentException {
        public CustomerNotFoundException(Long id) {
            super("Customer with id " + id + " not found");
        }
    }

    public static class ClientNotFoundException extends IllegalArgumentException{
        public ClientNotFoundException(Long id) {
            super("Client with id " + id + " not found");
        }
    }
}
