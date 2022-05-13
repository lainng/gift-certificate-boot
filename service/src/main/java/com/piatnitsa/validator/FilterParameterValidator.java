package com.piatnitsa.validator;

import com.piatnitsa.exception.ExceptionMessageKey;
import com.piatnitsa.exception.IncorrectParameterException;
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
     * @throws IncorrectParameterException if sort type not equal "asc" or "desc".
     */
    public static void validateSortType(MultiValueMap<String, String> filterParams) {
        for (Map.Entry<String, List<String>> entry : filterParams.entrySet()) {
            String key = entry.getKey().toLowerCase();
            switch (key) {
                case "tag_name_sort":
                case "name_sort":
                case "date_sort": {
                    List<String> sortTypeList = entry.getValue();
                    sortTypeList.stream()
                            .findFirst()
                            .ifPresent(FilterParameterValidator::validateType);
                    break;
                }
            }
        }
    }

    private static void validateType(String type) {
        if (type == null
                || type.isEmpty()
                || (!type.equalsIgnoreCase(ASCENDING) && !type.equalsIgnoreCase(DESCENDING))) {
            throw new IncorrectParameterException(ExceptionMessageKey.BAD_SORT_TYPE);
        }
    }
}
