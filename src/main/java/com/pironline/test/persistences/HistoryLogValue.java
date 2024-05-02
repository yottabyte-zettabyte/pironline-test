package com.pironline.test.persistences;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "history_logs_values")
@SuppressWarnings("PersistenceUnitPresent")
public class HistoryLogValue {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "field_id")
    private String fieldId;

    @Column(name = "new_value")
    private String newValue;

    @Column(name = "old_value")
    private String oldValue;
}
