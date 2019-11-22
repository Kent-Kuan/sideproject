package com.example.sideproject.Service;

import com.example.sideproject.Entity.User;

import java.util.List;

public interface UserService {
    public User findUserByEmail(String name);
    public List<User> findAllUser();
    public boolean registerUser(User user);
    public boolean login(User user);
    public boolean updateUser(User user);
}
