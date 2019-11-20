package com.example.sideproject.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class User {
    private String email;
    private String name;
    private String gender;
    private boolean isVIP;
    private String password;
}
