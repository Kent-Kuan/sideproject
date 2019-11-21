package com.example.sideproject.Controller;

import com.example.sideproject.Entity.ResponseBean;
import com.example.sideproject.Entity.User;
import com.example.sideproject.Service.UserService;
import com.example.sideproject.Utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @GetMapping("/test")
    public void test() {
        System.out.println("Hello world");
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseBean> login(@RequestBody User user) {
        if(user.getEmail()==null || user.getPassword()==null) {
            return new ResponseEntity<>(ResponseBean.error(403,"Username or password empty."),
                    HttpStatus.FORBIDDEN);
        }
        if(userService.login(user)) {
            Map<String, String> map = new HashMap<>();
            String token = jwtTokenUtil.generateToken(user);
            map.put("token", token);
            return new ResponseEntity<>(ResponseBean.ok("Login success.", map),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ResponseBean.error(403,"Username or password error."),
                    HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/users")
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
