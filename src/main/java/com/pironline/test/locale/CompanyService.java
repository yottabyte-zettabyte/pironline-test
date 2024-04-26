package com.pironline.test.locale;

import com.pironline.test.dto.CompanyDto;
import com.pironline.test.exceptions.BadRequestException;
import com.pironline.test.exceptions.NotFoundException;
import com.pironline.test.exceptions.handler.ErrorCode;
import com.pironline.test.mappers.CompanyMapper;
import com.pironline.test.persistences.Company;
import com.pironline.test.repositories.CompanyRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyMapper companyMapper;
    private final CompanyRepository companyRepository;

    public CompanyDto getById(UUID companyId) {
        if (companyId == null) {
            throw new BadRequestException(ErrorCode.ERROR_EMPTY_PARAMS);
        }

        Company company = companyRepository
                .findById(companyId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ERROR_NOT_FOUND, new Object[] {"Company", companyId.toString()}));

        return companyMapper.entityToDto(company);
    }
}
