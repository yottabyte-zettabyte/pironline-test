package com.pironline.test.service;

import com.pironline.test.dto.EmployeeDto;
import com.pironline.test.dto.EmployeeFullDto;
import com.pironline.test.exceptions.BadRequestException;
import com.pironline.test.exceptions.GenericException;
import com.pironline.test.exceptions.handler.ErrorCode;
import com.pironline.test.mappers.EmployeeMapper;
import com.pironline.test.persistences.Employee;
import com.pironline.test.repositories.EmployeeRepository;
import com.pironline.test.service.txn.EmployeeServiceTxn;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeMapper employeeMapper;
    private final EmployeeServiceTxn employeeServiceTxn;
    private final EmployeeRepository employeeRepository;

    public EmployeeFullDto getById(UUID employeeId) {
        if (employeeId == null) {
            throw new BadRequestException(ErrorCode.ERROR_EMPTY_PARAMS);
        }

        Employee employee = employeeRepository.get(employeeId);
        return employeeMapper.entityToFullDto(employee);
    }

    public EmployeeFullDto createEmployee(EmployeeDto employeeDto) {
        if (employeeDto == null) {
            throw new BadRequestException(ErrorCode.ERROR_EMPTY_PARAMS);
        }

        try {
            Employee employee = employeeMapper.dtoToEntity(employeeDto);
            employee = employeeServiceTxn.save(employee);
            return employeeMapper.entityToFullDto(employee);
        }
        catch (final Exception ex) {
            log.error("Error while creating employee with snils [{}]: ", employeeDto.getSnils(), ex);
            throw new GenericException(ErrorCode.ERROR_GENERIC, ex);
        }
    }
}
