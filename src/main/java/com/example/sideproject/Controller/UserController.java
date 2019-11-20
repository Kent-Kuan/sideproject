package com.example.sideproject.Controller;

import com.example.sideproject.Entity.User;
import com.example.sideproject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/test")
    public void test() {
        System.out.println("Hello world");
    }

    @PostMapping("/login")
    public void login() {
        System.out.println("login");
    }

    @GetMapping("/users")
    public List<User> users() {
        System.out.println(userService.findAllUser());
        return userService.findAllUser();
    }

    @PostMapping("/register")
    public void register() {
        System.out.println("register");
    }
}
