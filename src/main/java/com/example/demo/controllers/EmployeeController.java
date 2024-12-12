package com.example.demo.controllers;

import com.example.demo.dto.EmployeeRequestDto;
import com.example.demo.dto.EmployeeResponseDto;
import com.example.demo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getAllEmployeesForClient(@PathVariable Long clientId, @RequestParam int page, @RequestParam int size) {
        return employeeService.getAllEmployeesForClient(clientId, page, size);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }


    @PostMapping("create")
    public ResponseEntity<EmployeeResponseDto> addEmployee(@RequestBody EmployeeRequestDto employeeRequestDto) {
        return employeeService.addEmployee(employeeRequestDto);
    }


    @GetMapping("/designation")
    public ResponseEntity<?> getEmployeesByDesignation(@RequestParam String designation, @RequestParam int page, @RequestParam int size) {
        return employeeService.getEmployeesByDesignation(designation, page, size);
    }
}
