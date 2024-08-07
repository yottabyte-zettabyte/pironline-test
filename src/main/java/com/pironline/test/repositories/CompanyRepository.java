package com.pironline.test.repositories;

import com.pironline.test.exceptions.NotFoundException;
import com.pironline.test.exceptions.handler.ErrorCode;
import com.pironline.test.persistences.Company;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {

    default Company get(UUID id) {
        return findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ERROR_NOT_FOUND, new Object[] {"Company", id.toString()}));
    }
}
