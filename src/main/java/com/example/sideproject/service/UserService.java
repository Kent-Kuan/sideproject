package com.example.sideproject.service;

import com.example.sideproject.entity.User;
import com.example.sideproject.exception.CustomNoSuchElementException;

import java.util.List;

public interface UserService {
    public User findUserByEmail(String name);
    public List<User> findAllUser();
    public boolean registerUser(User user);
    public boolean login(User user);
    public boolean updateUser(User user);
    public boolean updateBalance(String email, int balance) throws CustomNoSuchElementException;
}
