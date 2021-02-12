package com.hillel.demo.core.application.controller;

import com.hillel.demo.core.application.dto.AddUserRequestDto;
import com.hillel.demo.core.application.dto.PageDto;
import com.hillel.demo.core.application.dto.UserDto;
import com.hillel.demo.core.application.facade.UserFacade;
import com.hillel.demo.core.database.entity.Gender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("User Controller")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserFacade userFacade;

    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Find all users")
    public PageDto<UserDto> findAll(
            @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize) {
        return userFacade.findAll(pageNum, pageSize);
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Find user with definite id")
    public UserDto getUser(@PathVariable Long id) throws NotFoundException {
        return userFacade.getUser(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Update user with definite id")
    public UserDto updateUser(@PathVariable Long id, @RequestBody AddUserRequestDto userDto) throws NotFoundException {
        return userFacade.updateUser(id, userDto);
    }

    @GetMapping("/sort=first_name&sortType=ASC")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Find users order by First Name Ascending")
    public PageDto<UserDto> findUsersByFirstNameAscending(
            @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize) {
        return userFacade.findUsersByFirstNameAscending(pageNum, pageSize);
    }

    @GetMapping("/sort=first_name&sortType=DESC")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Find users order by First Name Descending")
    public PageDto<UserDto> findUsersByFirstNameDescending(
            @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize) {
        return userFacade.findUsersByFirstNameDescending(pageNum, pageSize);
    }

    @GetMapping("/gender/{gender}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Sort users by Gender")
    public PageDto<UserDto> findUsersByGender
            (@PathVariable Gender gender,
             @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
             @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize) {
        return userFacade.findUsersByGender(gender, pageNum, pageSize);
    }

    @GetMapping("/sort=age&sortType=ASC")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Find users order by Age Ascending")
    public List<UserDto> findUsersByAgeAscending() {
        return userFacade.findUsersByAgeAscending();
    }

    @GetMapping("/sort=age&sortType=DESC")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Find users order by Age Descending")
    public List<UserDto> findUsersByAgeDescending() {
        return userFacade.findUsersByAgeDescending();
    }

    @GetMapping("/sort=last_name&sortType=ASC")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Find users order by Last Name Ascending")
    public List<UserDto> findUsersByFirstLastNameAscending() {
        return userFacade.findUsersByLastNameAscending();
    }

    @GetMapping("//sort=age&sortType=DESC")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Find users order by Last Name Descending")
    public List<UserDto> findUsersByFirstLastNameDescending() {
        return userFacade.findUsersByLastNameDescending();
    }

    @GetMapping("/{email}")
    @ApiOperation("Find user by email")
    public UserDto findUserByEmail(@PathVariable String email) throws NotFoundException {
        return userFacade.findUserByEmail(email);
    }
}
