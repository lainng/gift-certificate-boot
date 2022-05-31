package com.piatnitsa.dao.impl;

import com.piatnitsa.dao.AbstractDao;
import com.piatnitsa.dao.UserDao;
import com.piatnitsa.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Component
public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    public UserDaoImpl() {
        super(null, User.class);
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
    public List<User> findWithFilter(MultiValueMap<String, String> params, Pageable pageable) {
        throw new UnsupportedOperationException();
    }
}
