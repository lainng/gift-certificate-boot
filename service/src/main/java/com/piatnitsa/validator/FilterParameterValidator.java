package com.piatnitsa.validator;

import com.piatnitsa.exception.ExceptionMessageHolder;
import com.piatnitsa.exception.ExceptionMessageKey;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

/**
 * This class provides a validator for entity filter parameters.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public class FilterParameterValidator {
    private static final String ASCENDING = "asc";
    private static final String DESCENDING = "desc";

    /**
     * Validates sort order types.
     * @param filterParams filtering parameters.
     * @return the {@link ExceptionMessageHolder} object, which may contain the exception messages
     * thrown during sort type validation or be empty if no exceptions were thrown.
     */
    public static ExceptionMessageHolder validateSortType(MultiValueMap<String, String> filterParams) {
        ExceptionMessageHolder holder = new ExceptionMessageHolder();
        for (Map.Entry<String, List<String>> entry : filterParams.entrySet()) {
            String key = entry.getKey().toLowerCase();
            switch (key) {
                case "tag_name_sort":
                case "name_sort":
                case "date_sort": {
                    List<String> sortTypeList = entry.getValue();
                    sortTypeList.stream()
                            .findFirst()
                            .ifPresent(t -> validateType(t, holder));
                    break;
                }
            }
        }
        return holder;
    }

    /**
     * Validates pagination parameters.
     * @param page page index.
     * @param size the size of the page to be returned.
     * @return the {@link ExceptionMessageHolder} object, which may contain the exception messages
     * thrown during pagination parameters validation or be empty if no exceptions were thrown.
     */
    public static ExceptionMessageHolder validatePaginationParameters(int page, int size) {
        ExceptionMessageHolder holder = new ExceptionMessageHolder();
        validatePage(page, holder);
        validateSize(size, holder);
        return holder;
    }

    private static void validateType(String type, ExceptionMessageHolder holder) {
        if (type == null
                || type.isEmpty()
                || (!type.equalsIgnoreCase(ASCENDING) && !type.equalsIgnoreCase(DESCENDING))) {
            holder.putException(ExceptionMessageKey.BAD_SORT_TYPE, type);
        }
    }

    private static void validatePage(int page, ExceptionMessageHolder holder) {
        if (page < 0) {
            holder.putException(ExceptionMessageKey.BAD_PAGE_VALUE, page);
        }
    }

    private static void validateSize(int size, ExceptionMessageHolder holder) {
        if (size < 1) {
            holder.putException(ExceptionMessageKey.BAD_SIZE_VALUE, size);
        }
    }
}
