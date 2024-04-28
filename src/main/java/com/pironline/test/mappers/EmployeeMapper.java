package com.pironline.test.mappers;

import com.pironline.test.dto.EmployeeDto;
import com.pironline.test.dto.EmployeeFullDto;
import com.pironline.test.persistences.Employee;
import com.pironline.test.service.TitleService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class EmployeeMapper {

    @Autowired
    protected TitleService titleService;

    public abstract EmployeeDto entityToDto(Employee entity);

    @Mappings({
        @Mapping(target = "titleId", expression = "java(null)"),
        @Mapping(target = "title", expression = "java(titleService.getById(entity.getTitleId()))")
    })
    public abstract EmployeeFullDto entityToFullDto(Employee entity);

    public abstract Employee dtoToEntity(EmployeeDto dto);
}
