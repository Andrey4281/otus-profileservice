package com.example.profileservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {

    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
}
