package com.pironline.test.dto;

import com.pironline.test.persistences.HistoryLog;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryOutputDto {

    private List<HistoryLog> historyLogs = new ArrayList<>();

    private long totalCount = 0L;

    private boolean hasMore = false;
}
