package com.pironline.test.persistences;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "history_logs")
@SuppressWarnings("PersistenceUnitPresent")
public class HistoryLog {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "log_type")
    private String logType;

    @Column(name = "table_id")
    private String tableId;

    @Column(name = "object_id")
    private String objectId;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP", updatable = false)
    private LocalDateTime createdAt;

    @JoinColumn(name = "parent_log_id")
    @OneToMany(fetch = FetchType.EAGER)
    private List<HistoryLogValue> values;
}
