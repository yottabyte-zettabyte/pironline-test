package com.pironline.test.service;

import com.pironline.test.dto.EmployeeDto;
import com.pironline.test.dto.EmployeeFullDto;
import com.pironline.test.dto.EmployeePatchInputDto;
import com.pironline.test.exceptions.BadRequestException;
import com.pironline.test.exceptions.GenericException;
import com.pironline.test.exceptions.OptimisticLockException;
import com.pironline.test.exceptions.handler.ErrorCode;
import com.pironline.test.mappers.EmployeeMapper;
import com.pironline.test.persistences.Employee;
import com.pironline.test.repositories.EmployeeRepository;
import com.pironline.test.service.txn.EmployeeServiceTxn;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final Clock clock;
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

        if (employeeDto.getStartDate() == null) {
            employeeDto.setStartDate(LocalDate.now(clock));
        }

        if (employeeDto.getCompanyId() == null) {
            throw new BadRequestException(ErrorCode.ERROR_EMPTY_NAMED_PARAM, new Object[] {"companyId"});
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

    public void deleteById(UUID employeeId) {
        if (employeeId == null) {
            throw new BadRequestException(ErrorCode.ERROR_EMPTY_PARAMS);
        }

        try {
            employeeServiceTxn.delete(employeeId);
        }
        catch (final Exception ex) {
            handleExceptionWithOptimisticLock(employeeId, ex);
        }
    }

    public EmployeeFullDto leave(UUID employeeId, long currentVersion, LocalDate leaveDate) {
        if (employeeId == null) {
            throw new BadRequestException(ErrorCode.ERROR_EMPTY_PARAMS);
        }

        try {
            Employee updatedEmployee = processLeaveTransaction(employeeId, currentVersion, leaveDate);
            return employeeMapper.entityToFullDto(updatedEmployee);
        }
        catch (final Exception ex) {
            handleExceptionWithOptimisticLock(employeeId, ex);
            return null;
        }
    }

    public EmployeeFullDto changeTitle(UUID employeeId, long currentVersion, Integer newTitleId,
                                       LocalDate startDate, LocalDate leaveDate, BigDecimal newSalaryAmount) {
        if (employeeId == null) {
            throw new BadRequestException(ErrorCode.ERROR_EMPTY_PARAMS);
        }

        if (newTitleId == null) {
            throw new BadRequestException(ErrorCode.ERROR_EMPTY_NAMED_PARAM, new Object[] {"newTitleId"});

        }

        try {
            Employee updatedEmployee = processLeaveTransaction(employeeId, currentVersion, leaveDate);

            EmployeePatchInputDto updateDto = EmployeePatchInputDto
                    .builder()
                    .version(updatedEmployee.getVersion())
                    .newTitleId(newTitleId)
                    .startDate(Objects.nonNull(startDate) ? startDate : LocalDate.now(clock))
                    .leaveDate(LocalDate.MIN)
                    .salaryAmount(newSalaryAmount)
                    .build();
            updatedEmployee = employeeServiceTxn.update(employeeId, updateDto);
            return employeeMapper.entityToFullDto(updatedEmployee);
        }
        catch (final Exception ex) {
            handleExceptionWithOptimisticLock(employeeId, ex);
            return null;
        }
    }

    public EmployeeFullDto changeCompany(UUID employeeId, long currentVersion, UUID newCompanyId, Integer newTitleId,
                                         LocalDate startDate, LocalDate leaveDate, BigDecimal newSalaryAmount) {
        if (employeeId == null) {
            throw new BadRequestException(ErrorCode.ERROR_EMPTY_PARAMS);
        }

        if (newCompanyId == null) {
            throw new BadRequestException(ErrorCode.ERROR_EMPTY_NAMED_PARAM, new Object[] {"newCompanyId"});

        }
        if (newTitleId == null) {
            throw new BadRequestException(ErrorCode.ERROR_EMPTY_NAMED_PARAM, new Object[] {"newTitleId"});

        }

        try {
            Employee updatedEmployee = processLeaveTransaction(employeeId, currentVersion, leaveDate);

            EmployeePatchInputDto inputDto = EmployeePatchInputDto
                    .builder()
                    .version(updatedEmployee.getVersion())
                    .newCompanyId(newCompanyId)
                    .newTitleId(newTitleId)
                    .startDate(Objects.nonNull(startDate) ? startDate : LocalDate.now(clock))
                    .leaveDate(LocalDate.MIN)
                    .salaryAmount(newSalaryAmount)
                    .build();
            updatedEmployee = employeeServiceTxn.update(employeeId, inputDto);
            return employeeMapper.entityToFullDto(updatedEmployee);
        }
        catch (final Exception ex) {
            handleExceptionWithOptimisticLock(employeeId, ex);
            return null;
        }
    }

    private Employee processLeaveTransaction(UUID employeeId, long currentVersion, LocalDate leaveDate) {
        EmployeePatchInputDto inputDto = EmployeePatchInputDto
                .builder()
                .version(currentVersion)
                .leaveDate(Objects.nonNull(leaveDate) ? leaveDate : LocalDate.now(clock))
                .build();
        return employeeServiceTxn.update(employeeId, inputDto);
    }

    private void handleExceptionWithOptimisticLock(UUID employeeId, Exception ex) {
        log.error("Error while deleting employee with id [{}]: ", employeeId, ex);
        if (ex instanceof ConcurrencyFailureException) {
            throw new OptimisticLockException(ErrorCode.ERROR_OPTIMISTIC_LOCK, new Object[] {"Employee", employeeId.toString()});
        }
        else if (ex instanceof GenericException genericException) {
            throw genericException;
        }
        throw new GenericException(ErrorCode.ERROR_GENERIC, ex);
    }
}
