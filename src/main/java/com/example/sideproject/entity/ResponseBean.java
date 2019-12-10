package com.example.sideproject.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseBean {
    private Integer status;
    private String message;
    private Object data;

    public ResponseBean(Integer status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static ResponseBean ok() {
        return new ResponseBean(200, "", null);
    }
    public static ResponseBean ok(String message) {
        return new ResponseBean(200, message, null);
    }
    public static ResponseBean ok(String message, Object data) {
        return new ResponseBean(200, message, data);
    }
    public static ResponseBean ok(Object data) {
        return new ResponseBean(200, "", data);
    }
    public static ResponseBean error(String message) {
        return new ResponseBean(200, message, null);
    }
    public static ResponseBean error(String message, Object data) {
        return new ResponseBean(500, message, data);
    }
    public static ResponseBean error(Integer status, String message) {
        return new ResponseBean(status, message, null);
    }
    public static ResponseBean error(Integer status, String message, Object data) {
        return new ResponseBean(status, message, data);
    }
}
