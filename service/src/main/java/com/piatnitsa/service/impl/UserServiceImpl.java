package com.piatnitsa.service.impl;

import com.piatnitsa.dao.UserDao;
import com.piatnitsa.entity.User;
import com.piatnitsa.service.AbstractService;
import com.piatnitsa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Component
public class UserServiceImpl extends AbstractService<User> implements UserService {

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        super(userDao);
    }

    @Override
    public User insert(User entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<User> doFilter(MultiValueMap<String, String> params, int page, int size) {
        throw new UnsupportedOperationException();
    }
}
