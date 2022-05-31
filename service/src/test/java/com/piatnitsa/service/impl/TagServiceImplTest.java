package com.piatnitsa.service.impl;

import com.piatnitsa.dao.creator.FilterParameter;
import com.piatnitsa.dao.impl.TagDaoImpl;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.DuplicateEntityException;
import com.piatnitsa.exception.NoSuchEntityException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    TagDaoImpl tagDao;

    @InjectMocks
    TagServiceImpl tagService;

    private final Tag TAG_1 = new Tag(1, "tagName1");
    private final Tag TAG_2 = new Tag(2, "tagName3");
    private final Tag TAG_3 = new Tag(3, "tagName2");
    private final Tag TAG_4 = new Tag(4, "tagName4");

    private final Tag TAG_5 = new Tag(5, "TagName5");
    private final Tag NEW_INSERT_TAG = new Tag(0, "tagName5");
    private final Tag BEFORE_INSERT_TAG = new Tag(0, "TagName5");

    private static final long NOT_EXISTED_ID = 999L;
    private static final String PART_OF_TAG_NAME = "tagName";
    private static final String INCORRECT_FILTER_PARAM = "incorrectParameter";
    private static final String INCORRECT_FILTER_PARAM_VALUE = "incorrectParameterValue";
    private static final String ASCENDING = "ASC";
    private static final int PAGE = 0;
    private static final int SIZE = 5;

    @Test
    void getById_thenOk() {
        Mockito.when(tagDao.findById(TAG_1.getId())).thenReturn(Optional.of(TAG_1));
        Tag actual = tagService.getById(TAG_1.getId());
        assertEquals(TAG_1, actual);
    }

    @Test
    void getByNotExistedId_thenThrow() {
        Mockito.when(tagDao.findById(NOT_EXISTED_ID)).thenReturn(Optional.empty());
        assertThrows(NoSuchEntityException.class, () -> tagService.getById(NOT_EXISTED_ID));
    }

    @Test
    void getAll_thenOk() {
        PageRequest pageRequest = PageRequest.of(PAGE, SIZE);
        List<Tag> expected = Arrays.asList(TAG_1, TAG_2, TAG_5, TAG_4, TAG_5);
        Mockito.when(tagDao.findAll(pageRequest)).thenReturn(expected);
        List<Tag> actual = tagService.getAll(PAGE, SIZE);
        assertEquals(expected, actual);
    }

    @Test
    void doFilter_thenOk() {
        PageRequest pageRequest = PageRequest.of(PAGE, SIZE);

        MultiValueMap<String, String> filterParams = new LinkedMultiValueMap<>();
        filterParams.add(FilterParameter.TAG_NAME, PART_OF_TAG_NAME);
        filterParams.add(FilterParameter.SORT_BY_TAG_NAME, ASCENDING);

        List<Tag> expected = Arrays.asList(TAG_1, TAG_3, TAG_2, TAG_4);
        Mockito.when(tagDao.findWithFilter(filterParams, pageRequest)).thenReturn(expected);
        List<Tag> actual = tagService.doFilter(filterParams, PAGE, SIZE);
        assertEquals(expected, actual);
    }

    @Test
    void doWithIncorrectParamFilter_thenFetchAll() {
        PageRequest pageRequest = PageRequest.of(PAGE, SIZE);

        MultiValueMap<String, String> filterParams = new LinkedMultiValueMap<>();
        filterParams.add(INCORRECT_FILTER_PARAM, INCORRECT_FILTER_PARAM_VALUE);

        List<Tag> expected = Arrays.asList(TAG_1, TAG_2, TAG_3, TAG_4);
        Mockito.when(tagDao.findWithFilter(filterParams, pageRequest)).thenReturn(expected);
        List<Tag> actual = tagService.doFilter(filterParams, PAGE, SIZE);
        assertEquals(expected, actual);
    }

    @Test
    void insert_thenOk() {
        Mockito.when(tagDao.findByName(NEW_INSERT_TAG.getName())).thenReturn(Optional.empty());
        Mockito.when(tagDao.insert(BEFORE_INSERT_TAG)).thenReturn(TAG_5);
        Tag actual = tagService.insert(NEW_INSERT_TAG);
        assertEquals(TAG_5, actual);
    }

    @Test
    void insertAlreadyExistedTag_thenThrow() {
        Mockito.when(tagDao.findByName(NEW_INSERT_TAG.getName())).thenReturn(Optional.of(TAG_5));
        assertThrows(DuplicateEntityException.class, () -> tagService.insert(NEW_INSERT_TAG));
    }

    @Test
    void getMostPopularTagWithHighestCostOfAllOrders_tagExisted_thenOk() {
        Mockito.when(tagDao.findMostPopularTagWithHighestCostOfAllOrders()).thenReturn(Optional.of(TAG_4));
        Tag actual = tagService.getMostPopularTagWithHighestCostOfAllOrders();
        assertEquals(TAG_4, actual);
    }

    @Test
    void getMostPopularTagWithHighestCostOfAllOrders_tagNotExisted_thenThrow() {
        Mockito.when(tagDao.findMostPopularTagWithHighestCostOfAllOrders()).thenReturn(Optional.empty());
        assertThrows(NoSuchEntityException.class, () -> tagService.getMostPopularTagWithHighestCostOfAllOrders());
    }
}