package com.pironline.test.repositories;

import com.pironline.test.exceptions.NotFoundException;
import com.pironline.test.exceptions.handler.ErrorCode;
import com.pironline.test.persistences.Employee;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    default Employee get(UUID id) {
        return findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ERROR_NOT_FOUND, new Object[] {"Employee", id.toString()}));
    }

    Optional<Employee> findByIdAndDeletedIsFalse(UUID id);
}
