package com.pironline.test.service.txn;

import com.pironline.test.dto.EmployeePatchInputDto;
import com.pironline.test.exceptions.OptimisticLockException;
import com.pironline.test.exceptions.handler.ErrorCode;
import com.pironline.test.persistences.Employee;
import com.pironline.test.repositories.EmployeeRepository;
import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceTxn {

    private final EmployeeRepository employeeRepository;

    @Transactional
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Transactional
    public void delete(UUID employeeId) {
        Employee employee = employeeRepository.get(employeeId);
        employee.setDeleted(true);
    }

    @Transactional
    public Employee update(UUID employeeId, EmployeePatchInputDto inputDto) {
        Employee employee = employeeRepository.get(employeeId);
        if (employee.getVersion() != inputDto.getVersion()) {
            throw new OptimisticLockException(ErrorCode.ERROR_OPTIMISTIC_LOCK, new Object[] {"Employee", employeeId.toString()});
        }

        if (inputDto.getNewCompanyId() != null) {
            employee.setCompanyId(inputDto.getNewCompanyId());
        }

        if (inputDto.getNewTitleId() != null) {
            employee.setTitleId(inputDto.getNewTitleId());
        }

        if (inputDto.getStartDate() != null) {
            employee.setStartDate(inputDto.getStartDate());
        }

        if (inputDto.getLeaveDate() != null && !LocalDate.MIN.equals(inputDto.getLeaveDate())) {
            employee.setLeaveDate(inputDto.getLeaveDate());
        }
        else if (LocalDate.MIN.equals(inputDto.getLeaveDate())) {
            employee.setLeaveDate(null);
        }

        if (inputDto.getSalaryAmount() != null) {
            employee.setSalaryAmount(inputDto.getSalaryAmount());
        }

        return employee;
    }
}
