package com.example.sideproject.entity;

import lombok.Data;

import java.util.Date;

@Data
public class WinningNumbers {
    private int[] numbers;
    private String stage;
    private Date createTime;
}
