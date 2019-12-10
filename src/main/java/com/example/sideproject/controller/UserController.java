package com.example.sideproject.controller;

import com.example.sideproject.entity.ResponseBean;
import com.example.sideproject.entity.User;
import com.example.sideproject.service.UserService;
import com.example.sideproject.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @GetMapping("/test")
    public String test() {
        System.out.println("Hello world");
        return "Hello world";
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

    @GetMapping("/me")
    public ResponseEntity<ResponseBean> getUserInfo(Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        return new ResponseEntity<>(ResponseBean.ok(user), HttpStatus.OK);
    }

    @PutMapping("/me")
    public ResponseEntity<ResponseBean> updateUserInfo(Principal principal, @RequestBody User user) {
        if(user.getName() == null)
            return new ResponseEntity<>(ResponseBean.error(400, "User name cannot empty."),
                    HttpStatus.BAD_REQUEST);
        user.setEmail(principal.getName());
        if(userService.updateUser(user))
            return new ResponseEntity<>(ResponseBean.ok(), HttpStatus.OK);
        return new ResponseEntity<>(ResponseBean.error(403, "Update user name fail."),
                HttpStatus.FORBIDDEN);
    }

    @PostMapping("/users")
    public List<User> users() {
        System.out.println(userService.findAllUser());
        return userService.findAllUser();
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseBean> register(@RequestBody User user) {
        if(user.getPassword() == null || user.getEmail() == null || user.getEmail().trim().equals(""))
            return new ResponseEntity<>(ResponseBean.error(400, "Username or password empty."),
                    HttpStatus.BAD_REQUEST);
        user.setEmail(user.getEmail().trim());
        if(userService.registerUser(user)){
            Map<String, String> map = new HashMap<>();
            String token = jwtTokenUtil.generateToken(user);
            map.put("token", token);
            return new ResponseEntity<>(ResponseBean.ok("Login success.", map),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(ResponseBean.error(400, "Email already taken."),
                HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/logout")
    public ResponseEntity<ResponseBean> logout(Principal principal) {
        System.out.println(String.format("User: %s logout.", principal.getName()));
        return new ResponseEntity<>(ResponseBean.ok(), HttpStatus.OK);
    }

    @GetMapping("/refreshToken")
    public ResponseEntity<ResponseBean> refreshToken(Principal principal) {
        String userEmail = principal.getName();
        System.out.println(String.format("User: %s refresh token.", userEmail));

        User user = new User();
        user.setEmail(userEmail);
        Map<String, String> map = new HashMap<>();
        String token = jwtTokenUtil.generateToken(user);
        map.put("token", token);
        return new ResponseEntity<>(ResponseBean.ok("Login success.", map),
                HttpStatus.OK);
    }
}
