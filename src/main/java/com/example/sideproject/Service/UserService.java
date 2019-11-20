package com.example.sideproject.Service;

import com.example.sideproject.Entity.User;

import java.util.List;

public interface UserService {
    public User findUserByName(String name);
    public List<User> findAllUser();
    public boolean registerUser(User user);
    public boolean login(User user);
}
