package com.piatnitsa.service.impl;

import com.piatnitsa.dao.impl.UserDaoImpl;
import com.piatnitsa.entity.User;
import com.piatnitsa.exception.NoSuchEntityException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private static final long NOT_EXISTED_ID = 999L;
    private static final int PAGE = 0;
    private static final int SIZE = 5;

    private final User USER_1 = new User(1, "name1");
    private final User USER_2 = new User(2, "name2");
    private final User USER_3 = new User(3, "name3");

    @Mock
    UserDaoImpl userDao;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void getById_thenOk() {
        Mockito.when(userDao.findById(USER_1.getId())).thenReturn(Optional.of(USER_1));
        User actual = userService.getById(USER_1.getId());
        assertEquals(USER_1, actual);
    }

    @Test
    void getNotExistedById_thenThrowEx() {
        Mockito.when(userDao.findById(NOT_EXISTED_ID)).thenReturn(Optional.empty());
        assertThrows(NoSuchEntityException.class, () -> userService.getById(NOT_EXISTED_ID));
    }

    @Test
    void getAll_thenOk() {
        List<User> expected = Arrays.asList(USER_1, USER_2, USER_3);
        Pageable pageRequest = PageRequest.of(PAGE, SIZE);
        Mockito.when(userDao.findAll(pageRequest)).thenReturn(expected);

        List<User> actual = userService.getAll(PAGE, SIZE);
        assertEquals(expected, actual);
    }

}