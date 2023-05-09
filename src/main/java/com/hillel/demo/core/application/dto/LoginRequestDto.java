package com.hillel.demo.core.application.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Setter
@Getter
@ApiModel(value = "Login User Request", description = "Request Model for user login")
public class LoginRequestDto {
    @Email
    private String username;
    private String password;
}
