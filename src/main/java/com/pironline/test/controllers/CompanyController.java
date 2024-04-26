package com.pironline.test.controllers;

import com.pironline.test.dto.CompanyDto;
import com.pironline.test.locale.CompanyService;
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
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity createCompany(@RequestBody @Valid CompanyDto requestBody) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyDto> getCompanyById(@PathVariable(name = "companyId", required = true) @NotNull UUID companyId) {
        CompanyDto response = companyService.getById(companyId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
