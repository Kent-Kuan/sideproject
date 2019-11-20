package com.example.sideproject.Controller;

import com.example.sideproject.Entity.ResponseBean;
import com.example.sideproject.Entity.User;
import com.example.sideproject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ResponseBean> register(@RequestBody User user) {
        if(userService.registerUser(user))
            return new ResponseEntity<ResponseBean>(ResponseBean.ok(), HttpStatus.OK);
        return new ResponseEntity<ResponseBean>(ResponseBean.error(400, "Email already taken."),
                HttpStatus.BAD_REQUEST);
    }
}
