package com.pironline.test.services.txn;

import com.pironline.test.dto.CompanyFullDto;
import com.pironline.test.exceptions.OptimisticLockException;
import com.pironline.test.exceptions.handler.ErrorCode;
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
        if (company.getVersion() != companyDto.getVersion()) {
            throw new OptimisticLockException(ErrorCode.ERROR_OPTIMISTIC_LOCK, new Object[] {"Company", companyId.toString()});
        }
        
        company.setShortName(companyDto.getShortName());
        company.setLongName(companyDto.getLongName());
        company.setDescription(companyDto.getDescription());
        company.setInn(companyDto.getInn());
    }
}
