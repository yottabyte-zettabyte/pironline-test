package com.pironline.test.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;

@UtilityClass
public class PagingUtils {

    private static final int DEFAULT_OFFSET = 0;
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 10;

    public static int getSizeOrDefault(Integer size) {
        if (size == null || size < 0) {
            size = DEFAULT_PAGE_SIZE;
        }
        return size;
    }

    public static int getOffsetOrDefault(Integer offset) {
        if (offset == null || offset < 0) {
            offset = DEFAULT_OFFSET;
        }
        return offset;
    }

    public static int getPageOrDefault(Integer size, Integer offset) {
        if (offset == null || size == null || size < 1) {
            return DEFAULT_PAGE;
        }
        return offset / size;
    }

    public static Sort.Direction getSortDirectionOrDefault(String sortDirection, Sort.Direction defaultSortDirection) {
        if (StringUtils.isNotBlank(sortDirection)) {
            if (sortDirection.toUpperCase().equals(Sort.Direction.ASC.toString())) {
                return Sort.Direction.ASC;
            } else if (sortDirection.toUpperCase().equals(Sort.Direction.DESC.toString())) {
                return Sort.Direction.DESC;
            }
        }
        return defaultSortDirection;
    }
}
