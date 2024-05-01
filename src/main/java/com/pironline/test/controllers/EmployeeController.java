package com.pironline.test.controllers;

import com.pironline.test.dto.EmployeeDto;
import com.pironline.test.dto.EmployeeFullDto;
import com.pironline.test.dto.EmployeePatchInputDto;
import com.pironline.test.services.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    public ResponseEntity<EmployeeFullDto> getEmployee(@PathVariable(name = "employeeId", required = true) @NotNull UUID employeeId) {
        EmployeeFullDto employee = employeeService.getById(employeeId);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EmployeeFullDto> createEmployee(@RequestBody @Valid EmployeeDto requestBody) {
        EmployeeFullDto employee = employeeService.createEmployee(requestBody);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<EmployeeFullDto> deleteEmployee(@PathVariable(name = "employeeId", required = true) @NotNull UUID employeeId) {
        employeeService.deleteById(employeeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{employeeId}/leave")
    public ResponseEntity<EmployeeFullDto> leaveEmployee(@PathVariable(name = "employeeId", required = true) @NotNull UUID employeeId,
                                                         @RequestBody @Valid EmployeePatchInputDto requestBody) {
        EmployeeFullDto employee = employeeService.leave(employeeId, requestBody.getVersion(), requestBody.getLeaveDate());
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PatchMapping("/{employeeId}/new/title")
    public ResponseEntity<EmployeeFullDto> changeTitle(@PathVariable(name = "employeeId", required = true) @NotNull UUID employeeId,
                                                         @RequestBody @Valid EmployeePatchInputDto requestBody) {
        EmployeeFullDto employee = employeeService.changeTitle(employeeId, requestBody);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PatchMapping("/{employeeId}/new/company")
    public ResponseEntity<EmployeeFullDto> changeCompany(@PathVariable(name = "employeeId", required = true) @NotNull UUID employeeId,
                                                         @RequestBody @Valid EmployeePatchInputDto requestBody) {
        EmployeeFullDto employee = employeeService.changeCompany(employeeId, requestBody);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
}
