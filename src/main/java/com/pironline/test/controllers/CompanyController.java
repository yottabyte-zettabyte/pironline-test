package com.pironline.test.controllers;

import com.pironline.test.dto.CompanyDto;
import com.pironline.test.dto.CompanyFullDto;
import com.pironline.test.dto.HistoryOutputDto;
import com.pironline.test.services.CompanyService;
import com.pironline.test.services.HistoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;
    private final HistoryService historyService;

    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyFullDto> getCompany(@PathVariable(name = "companyId", required = true) @NotNull UUID companyId) {
        CompanyFullDto company = companyService.getById(companyId);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CompanyFullDto> createCompany(@RequestBody @Valid CompanyDto requestBody) {
        CompanyFullDto company = companyService.createCompany(requestBody);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @PutMapping("/{companyId}")
    public ResponseEntity<CompanyFullDto> updateCompany(@PathVariable(name = "companyId", required = true) @NotNull UUID companyId,
                                                        @RequestBody @Valid CompanyFullDto requestBody) {
        CompanyFullDto company = companyService.updateCompany(companyId, requestBody);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @GetMapping("/{companyId}/history")
    public ResponseEntity<HistoryOutputDto> getHistory(@PathVariable(name = "companyId", required = true) @NotNull UUID companyId,
                                                       @RequestParam(name = "size", required = false) Integer size,
                                                       @RequestParam(name = "offset", required = false) Integer offset,
                                                       @RequestParam(name = "sortDirection", required = false, defaultValue = "desc") String sortDirection) {
        HistoryOutputDto output = historyService.getHistory("companies", List.of(companyId.toString()), size, offset, sortDirection);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }
}
