package com.hillel.demo.core.application.contoller;

import com.hillel.demo.core.application.dto.AddUserRequestDto;
import com.hillel.demo.core.domain.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api("Registration Controller")
@RestController
public class RegistrationController {

    private final UserService userDetailsService;

    public RegistrationController(UserService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping(value = "/registration")
    @ApiOperation("Registration procedure")
    public String signUpNewUser(@RequestBody @Valid AddUserRequestDto userDto) throws NotFoundException {
        boolean existing = userDetailsService.existsByEmail(userDto.getEmail());

        if (existing != false) {
            return "There is already an account registered with current email";
        }
        userDetailsService.singUpNewUser(userDto);
        return "You are successfully signed up. ";
    }
}
