package com.pironline.test.repositories;

import com.pironline.test.persistences.HistoryLog;
import com.pironline.test.repositories.specs.HistoryLogSpecification;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryLogRepository extends JpaRepository<HistoryLog, Long>, JpaSpecificationExecutor<HistoryLog> {

    default Page<HistoryLog> findHistoryLogs(String tableId, List<String> objetcIds, Pageable pageable) {
        return findAll(HistoryLogSpecification.create(tableId, objetcIds), pageable);
    }
}
