package com.example.demo.dto;

import com.example.demo.models.Client;
import com.example.demo.models.Customer;
import com.example.demo.models.Employees;
import com.example.demo.models.Ticket;
import com.example.demo.repo.ClientRepository;
import com.example.demo.services.CustomerService;

import java.util.Optional;

public class Mapper {

    public static Client toClientEntity(ClientResponseDto clientResponseDto) {
        Client client = new Client();

        client.setClientCode(clientResponseDto.getClientCode());
        client.setClientName(clientResponseDto.getClientName());
        client.setCreatedDate(clientResponseDto.getCreatedDate());
        client.setEnabled(clientResponseDto.isEnabled());
        client.setLastModifiedDate(clientResponseDto.getLastModifiedDate());
        return client;

    }
    public static ClientResponseDto toClientDto(Client client) {
        ClientResponseDto clientResponseDto = new ClientResponseDto();
        clientResponseDto.setClientName(client.getClientName());
        clientResponseDto.setId(client.getId());
        clientResponseDto.setClientCode(client.getClientCode());
        clientResponseDto.setClientName(client.getClientName());
        clientResponseDto.setEnabled(client.isEnabled());
        clientResponseDto.setCreatedDate(client.getCreatedDate());
        clientResponseDto.setLastModifiedDate(client.getLastModifiedDate());
        return clientResponseDto;

    }

    public static CustomerResponseDto toCustomerDto(Customer customer) {
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        customerResponseDto.setId(customer.getId());
        customerResponseDto.setId(customer.getId());
        customerResponseDto.setCustomerName(customer.getCustomerName());
        customerResponseDto.setEmail(customer.getEmail());
        customerResponseDto.setPhoneNumber(customer.getPhoneNumber());
        customerResponseDto.setEnabled(customer.isEnabled());
        customerResponseDto.setCustomerCode(customer.getCustomerCode());
        return customerResponseDto;
    }



    public static Customer toCustomerEntity(CustomerRequestDto customerRequestDto, ClientRepository clientRepository) {
        Customer customer = new Customer();
        customer.setCustomerName(customerRequestDto.getCustomerName());
        customer.setEmail(customerRequestDto.getEmail());
        customer.setPhoneNumber(customerRequestDto.getPhoneNumber());
        customer.setEnabled(customerRequestDto.isEnabled());
        Long clientId = customerRequestDto.getClientId();
        Optional<Client> clientOptional = clientRepository.findById(clientId);

        if (clientOptional.isPresent()) {
            customer.setClient(clientOptional.get());
        } else {
            throw new CustomerService.ClientNotFoundException(clientId);
        }

        return customer;
    }



    public static EmployeeResponseDto getEmployeeResponseDto(Employees employee) {
        EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto();
        employeeResponseDto.setId(employee.getId());
        employeeResponseDto.setId(employee.getId());
        employeeResponseDto.setName(employee.getName());
        employeeResponseDto.setDesignation(employee.getDesignation());
        employeeResponseDto.setEmployeeCode(employee.getEmployeeCode());
        employeeResponseDto.setEnabled(employee.isEnabled());
        employeeResponseDto.setLastModifiedDate(employee.getLastModifiedDate());
        return employeeResponseDto;
    }

    public static Employees toEmployeeEntity(EmployeeResponseDto employeeResponseDto) {
        Employees employees = new Employees();

        return employees;

    }


    public static TicketResponseDto mapToTicketResponseDto(Ticket ticket) {
        TicketResponseDto ticketResponseDto = new TicketResponseDto();
        ticketResponseDto.setId(ticket.getId());
        ticketResponseDto.setTicketCode(ticket.getTicketCode());
        ticketResponseDto.setTitle(ticket.getTitle());
        ticketResponseDto.setStatus(ticket.getStatus());
        ticketResponseDto.setLastModifiedDate(ticket.getLastModifiedDate());
        return ticketResponseDto;
    }

    public static Ticket ticketEntity(TicketRequestDto ticketRequestDto) {
        Ticket ticket = new Ticket();
        ticket.setId(ticketRequestDto.getId());
        ticket.setTicketCode(ticketRequestDto.getTicketCode());
        ticket.setTitle(ticketRequestDto.getTitle());
        ticket.setStatus(ticketRequestDto.getStatus());
        return ticket;
    }
}

