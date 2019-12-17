package com.example.sideproject.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
public class Order {
    private int[] numbers;
    private String stage;
    private int id;
    private Date createTime;
    private int winning;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userId;
}