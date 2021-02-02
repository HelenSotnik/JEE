package com.hillel.demo.core.application.contoller;


import com.hillel.demo.core.application.dto.AddUserRequestDto;
import com.hillel.demo.core.application.dto.UserDto;
import com.hillel.demo.core.application.facade.UserFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api("Admin Controller, saves and deletes users from database")
@RequestMapping("/api/admin")
@RestController
public class AdminController {

    private final UserFacade userFacade;

    public AdminController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Delete user from DB")
    public void deleteUser(@PathVariable Long id) throws NotFoundException {
        userFacade.deleteUser(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Save new user to DB")
    public UserDto saveUser(@RequestBody @Valid AddUserRequestDto userDto) {
        return userFacade.saveUser(userDto);
    }
}
