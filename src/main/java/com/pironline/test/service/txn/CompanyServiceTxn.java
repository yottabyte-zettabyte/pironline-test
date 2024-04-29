package com.pironline.test.service.txn;

import com.pironline.test.dto.CompanyFullDto;
import com.pironline.test.persistences.Company;
import com.pironline.test.repositories.CompanyRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyServiceTxn {

    private final CompanyRepository companyRepository;

    @Transactional
    public Company save(Company company) {
        return companyRepository.save(company);
    }
    
    @Transactional
    public void update(UUID companyId, CompanyFullDto companyDto) {
        Company company = companyRepository.get(companyId);
        company.setShortName(companyDto.getShortName());
        company.setLongName(companyDto.getLongName());
        company.setDescription(companyDto.getDescription());
        company.setInn(companyDto.getInn());
    }
}
