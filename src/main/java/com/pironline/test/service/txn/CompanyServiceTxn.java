package com.pironline.test.service.txn;

import com.pironline.test.persistences.Company;
import com.pironline.test.repositories.CompanyRepository;
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
}
