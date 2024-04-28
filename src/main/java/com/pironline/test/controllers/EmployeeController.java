package com.pironline.test.controllers;

import com.pironline.test.dto.EmployeeDto;
import com.pironline.test.dto.EmployeeFullDto;
import com.pironline.test.service.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeFullDto> getEmployeeById(@PathVariable(name = "employeeId", required = true) @NotNull UUID employeeId) {
        EmployeeFullDto employee = employeeService.getById(employeeId);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EmployeeFullDto> createCompany(@RequestBody @Valid EmployeeDto requestBody) {
        EmployeeFullDto employee = employeeService.createEmployee(requestBody);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
}
