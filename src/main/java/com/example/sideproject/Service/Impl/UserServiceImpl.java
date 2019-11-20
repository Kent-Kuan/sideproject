package com.example.sideproject.Service.Impl;

import com.example.sideproject.Dao.UserDao;
import com.example.sideproject.Entity.User;
import com.example.sideproject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    @Override
    public boolean registerUser(User user) {
        Integer result;
        try {
            result = userDao.insertUser(user.getName(), user.getPassword());
        } catch (DataIntegrityViolationException e) {
            result = 0;
            System.out.println("Email already taken ...");
        }
        return result == 1 ? true : false;
    }
}
