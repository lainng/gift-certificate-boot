package com.piatnitsa.validator;

import com.piatnitsa.dao.creator.FilterParameter;
import com.piatnitsa.exception.ExceptionMessageHolder;
import com.piatnitsa.exception.ExceptionMessageKey;
import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilterParameterValidatorTest {
    private static final String CORRECT_SORT_TYPE = "asc";
    private static final String INCORRECT_SORT_TYPE = "aasc";
    private static final int CORRECT_PAGE = 1;
    private static final int INCORRECT_PAGE = -1;
    private static final int CORRECT_SIZE = 1;
    private static final int INCORRECT_SIZE = 0;

    @Test
    void validateSortType_thenNoException() {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add(FilterParameter.NAME_SORT, CORRECT_SORT_TYPE);
        paramMap.add(FilterParameter.DATE_SORT, CORRECT_SORT_TYPE);
        ExceptionMessageHolder holder = FilterParameterValidator.validateSortType(paramMap);
        assertFalse(holder.hasMessages());
    }

    @Test
    void validateIncorrectSortType_thenHasExceptions() {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add(FilterParameter.SORT_BY_TAG_NAME, INCORRECT_SORT_TYPE);
        ExceptionMessageHolder holder = FilterParameterValidator.validateSortType(paramMap);
        assertTrue(holder.hasMessages());
        assertTrue(holder.getMessages().containsKey(ExceptionMessageKey.BAD_SORT_TYPE));
    }

    @Test
    void validatePaginationParameters_thenNoException() {
        ExceptionMessageHolder holder = FilterParameterValidator
                .validatePaginationParameters(CORRECT_PAGE, CORRECT_SIZE);
        assertFalse(holder.hasMessages());
    }

    @Test
    void validateIncorrectPageParameter_thenHasException() {
        ExceptionMessageHolder holder = FilterParameterValidator
                .validatePaginationParameters(INCORRECT_PAGE, CORRECT_SIZE);
        assertTrue(holder.hasMessages());
        assertTrue(holder.getMessages().containsKey(ExceptionMessageKey.BAD_PAGE_VALUE));
    }

    @Test
    void validateIncorrectSizeParameter_thenHasException() {
        ExceptionMessageHolder holder = FilterParameterValidator
                .validatePaginationParameters(CORRECT_PAGE, INCORRECT_SIZE);
        assertTrue(holder.hasMessages());
        assertTrue(holder.getMessages().containsKey(ExceptionMessageKey.BAD_SIZE_VALUE));
    }
}