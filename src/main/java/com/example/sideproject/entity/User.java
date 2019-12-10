package com.example.sideproject.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private String email;
    private String name;
    private Gender gender;
    @JsonProperty("isVIP")
    private boolean isVIP;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
