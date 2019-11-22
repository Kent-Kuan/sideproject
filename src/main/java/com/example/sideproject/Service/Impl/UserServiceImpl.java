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
    public User findUserByEmail(String name) {
        return userDao.findUserByEmail(name);
    }

    @Override
    public List<User> findAllUser() {
        return userDao.findAllUser();
    }

    @Override
    public boolean login(User user) {
        User foundUser = userDao.findUserByEmail(user.getEmail());
        if(foundUser!= null && foundUser.getPassword().equals(user.getPassword())){
            return true;
        }
        return false;
    }

    @Override
    public boolean registerUser(User user) {
        Integer result;
        try {
            result = userDao.insertUser(user.getEmail(), user.getPassword());
        } catch (DataIntegrityViolationException e) {
            result = 0;
            System.out.println("Email already taken ...");
        }
        return result == 1;
    }

    @Override
    public boolean updateUser(User user) {
        Integer result;
        result = userDao.updateUser(user.getEmail(), user.getName());
        return result == 1;
    }
}
