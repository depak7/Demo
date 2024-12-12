package com.example.demo.services;

import com.example.demo.dto.EmployeeRequestDto;
import com.example.demo.dto.EmployeeResponseDto;
import com.example.demo.dto.Mapper;
import com.example.demo.models.Employees;
import com.example.demo.repo.ClientRepository;
import com.example.demo.repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ClientRepository clientRepository;

    public ResponseEntity<Page<EmployeeResponseDto>> getAllEmployeesForClient(Long clientId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Employees> employeePage = employeeRepository.findByClientId(clientId, pageable);
        Page<EmployeeResponseDto> employeeResponseDtoPage = employeePage.map(Mapper::getEmployeeResponseDto);
        return new ResponseEntity<>(employeeResponseDtoPage, HttpStatus.OK);

    }


    public ResponseEntity<EmployeeResponseDto> getEmployeeById(int id) {
        Optional<Employees> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isEmpty()) {
            throw new EmployeeNotFoundException(id);
        }
        Employees employee = employeeOptional.get();
        EmployeeResponseDto employeeResponseDto= Mapper.getEmployeeResponseDto(employee);
        return new ResponseEntity<>(employeeResponseDto, HttpStatus.OK);
    }

    private String generateEmployeeCode(long clientId, String name) {

        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return "EM" + clientId + "-" + timestamp;
    }


    public ResponseEntity<EmployeeResponseDto> addEmployee(EmployeeRequestDto employeeRequestDto) {
        System.out.println(employeeRequestDto);
        if (!clientRepository.existsById(employeeRequestDto.getClientId())) {
            throw new ClientNotFoundException(employeeRequestDto.getClientId());
        }

        String generatedEmployeeCode = generateEmployeeCode(employeeRequestDto.getClientId(), employeeRequestDto.getName());
        Employees employee = new Employees();
        employee.setName(employeeRequestDto.getName());
        employee.setDesignation(employeeRequestDto.getDesignation());
        employee.setEnabled(employeeRequestDto.isEnabled());
        employee.setEmployeeCode(generatedEmployeeCode);
        employee.setClient(clientRepository.findById(employeeRequestDto.getClientId()).get());
        employee.setLastModifiedDate(new Date());


        Employees savedEmployee = employeeRepository.save(employee);


        EmployeeResponseDto employeeResponseDto = Mapper.getEmployeeResponseDto(savedEmployee);


        return new ResponseEntity<>(employeeResponseDto, HttpStatus.CREATED);
    }


    public ResponseEntity<Page<EmployeeResponseDto>> getEmployeesByDesignation(String designation, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Employees> employeePage = employeeRepository.findByDesignation(designation, pageable);

        Page<EmployeeResponseDto> employeeResponseDtoPage = employeePage.map(Mapper::getEmployeeResponseDto);

        return new ResponseEntity<>(employeeResponseDtoPage, HttpStatus.OK);
    }

    public static class EmployeeNotFoundException extends RuntimeException {
        public EmployeeNotFoundException(int id) {
            super("Employee with id " + id + " not found");
        }
    }

    public static class ClientNotFoundException extends RuntimeException {
        public ClientNotFoundException(Long id) {
            super("Client with id " + id + " not found");
        }
    }
}
