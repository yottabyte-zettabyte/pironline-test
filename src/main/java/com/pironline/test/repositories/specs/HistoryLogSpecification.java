package com.pironline.test.repositories.specs;

import com.pironline.test.persistences.HistoryLog;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

public class HistoryLogSpecification {

    public static Specification<HistoryLog> create(String tableId, List<String> objectIds) {

        return (Root<HistoryLog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(tableId)) {
                predicates.add(criteriaBuilder.equal(root.get("tableId"), tableId));
            }

            if (!CollectionUtils.isEmpty(objectIds)) {
                predicates.add(root.get("objectId").in(objectIds));
            }

            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }
}
