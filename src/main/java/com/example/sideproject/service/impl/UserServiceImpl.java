package com.example.sideproject.service.impl;

import com.example.sideproject.dao.UserDao;
import com.example.sideproject.entity.User;
import com.example.sideproject.exception.CustomNoSuchElementException;
import com.example.sideproject.service.UserService;
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

    @Override
    public boolean updateBalance(String email, int balance) throws CustomNoSuchElementException {
        User user = userDao.findUserByEmail(email);
        if(user == null) {
            throw new CustomNoSuchElementException(997, "Cannot find this user.");
        }
        int currentBalance = user.getBalance();
        balance = currentBalance + balance;
        if(userDao.updateBalanceWithEmail(balance, user.getEmail()) != 1)
            throw new CustomNoSuchElementException(899, "Add value fail.");
        return true;
    }
}
