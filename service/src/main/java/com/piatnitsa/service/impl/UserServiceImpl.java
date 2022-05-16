package com.piatnitsa.service.impl;

import com.piatnitsa.dao.UserDao;
import com.piatnitsa.entity.User;
import com.piatnitsa.service.AbstractService;
import com.piatnitsa.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl extends AbstractService<User> implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        super(userDao);
        this.userDao = userDao;
    }

    @Override
    public User insert(User entity) {
        return userDao.insert(entity);
    }
}
