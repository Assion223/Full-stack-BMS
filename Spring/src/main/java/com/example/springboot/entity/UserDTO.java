package com.example.springboot.entity;

import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String avatorUrl;
    private String token;
}
