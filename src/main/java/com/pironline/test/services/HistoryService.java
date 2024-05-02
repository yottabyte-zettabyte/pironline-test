package com.pironline.test.services;

import com.pironline.test.dto.HistoryOutputDto;
import com.pironline.test.exceptions.BadRequestException;
import com.pironline.test.exceptions.GenericException;
import com.pironline.test.exceptions.handler.ErrorCode;
import com.pironline.test.persistences.HistoryLog;
import com.pironline.test.repositories.HistoryLogRepository;
import com.pironline.test.utils.PagingUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryLogRepository historyLogRepository;

    public HistoryOutputDto getHistory(String tableId, List<String> objectIds, Integer sizeParam, Integer offsetParam, String sortDirectionParam) {
        if (StringUtils.isBlank(tableId) || CollectionUtils.isEmpty(objectIds)) {
            throw new BadRequestException(ErrorCode.ERROR_EMPTY_PARAMS);
        }

        int size = PagingUtils.getSizeOrDefault(sizeParam);
        int offset = PagingUtils.getOffsetOrDefault(offsetParam);
        int page = PagingUtils.getPageOrDefault(size, offset);
        Sort.Direction sortDirection = PagingUtils.getSortDirectionOrDefault(sortDirectionParam, Sort.Direction.DESC);

        try {
            PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortDirection, "id"));
            Page<HistoryLog> historyLogsPage = historyLogRepository.findHistoryLogs(tableId, objectIds, pageRequest);
            List<HistoryLog> historyLogs = historyLogsPage.getContent();
            if (CollectionUtils.isEmpty(historyLogs)) {
                return new HistoryOutputDto();
            }

            return new HistoryOutputDto(historyLogs, historyLogsPage.getTotalElements(), historyLogsPage.hasNext());
        }
        catch (final Exception ex) {
            log.error("Error while retrieving history logs for [{}] with ids [{}]: ", tableId, objectIds.toString(), ex);
            throw new GenericException(ErrorCode.ERROR_GENERIC, ex);
        }
    }
}
