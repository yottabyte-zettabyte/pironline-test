package com.pironline.test.service;

import com.pironline.test.dto.CompanyDto;
import com.pironline.test.dto.CompanyFullDto;
import com.pironline.test.exceptions.BadRequestException;
import com.pironline.test.exceptions.GenericException;
import com.pironline.test.exceptions.OptimisticLockException;
import com.pironline.test.exceptions.handler.ErrorCode;
import com.pironline.test.mappers.CompanyMapper;
import com.pironline.test.persistences.Company;
import com.pironline.test.repositories.CompanyRepository;
import com.pironline.test.service.txn.CompanyServiceTxn;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyMapper companyMapper;
    private final CompanyServiceTxn companyServiceTxn;
    private final CompanyRepository companyRepository;

    public CompanyFullDto getById(UUID companyId) {
        if (companyId == null) {
            throw new BadRequestException(ErrorCode.ERROR_EMPTY_PARAMS);
        }

        Company company = companyRepository.get(companyId);
        return companyMapper.entityToFullDto(company);
    }

    public CompanyFullDto createCompany(CompanyDto companyDto) {
        if (companyDto == null) {
            throw new BadRequestException(ErrorCode.ERROR_EMPTY_PARAMS);
        }

        try {
            Company company = companyMapper.dtoToEntity(companyDto);
            company = companyServiceTxn.save(company);
            return companyMapper.entityToFullDto(company);
        }
        catch (final Exception ex) {
            log.error("Error while creating company with name [{}]: ", companyDto.getShortName(), ex);
            throw new GenericException(ErrorCode.ERROR_GENERIC, ex);
        }
    }

    public CompanyFullDto updateCompany(UUID companyId, CompanyFullDto companyDto) {
        if (companyId == null || companyDto == null) {
            throw new BadRequestException(ErrorCode.ERROR_EMPTY_PARAMS);
        }
        
        try {
            companyServiceTxn.update(companyId, companyDto);

            Company company = companyRepository.get(companyId);
            return companyMapper.entityToFullDto(company);
        }
        catch (final Exception ex) {
            log.error("Error while updating company with id [{}]: ", companyDto.getId(), ex);
            if (ex instanceof ConcurrencyFailureException) {
                throw new OptimisticLockException(ErrorCode.ERROR_OPTIMISTIC_LOCK, new Object[] {"Company", companyDto.getId().toString()});
            }
            else if (ex instanceof GenericException genericException) {
                throw genericException;
            }
            throw new GenericException(ErrorCode.ERROR_GENERIC, ex);
        }
    }
}
