package com.example.sideproject.Service.Impl;

import com.example.sideproject.Dao.UserDao;
import com.example.sideproject.Entity.User;
import com.example.sideproject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Override
    public User findUserByName(String name) {
        return userDao.findUserByName(name);
    }

    @Override
    public List<User> findAllUser() {
        return userDao.findAllUser();
    }
}
