package com.pironline.test.mappers;

import com.pironline.test.dto.CompanyDto;
import com.pironline.test.persistences.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    CompanyDto entityToDto(Company entity);

    Company dtoToEntity(CompanyDto dto);
}
