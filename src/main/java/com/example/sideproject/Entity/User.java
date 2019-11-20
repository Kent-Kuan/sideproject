package com.example.sideproject.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class User {
    private String name;
    private String password;
}
