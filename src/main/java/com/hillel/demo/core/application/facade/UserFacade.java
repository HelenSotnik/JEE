package com.hillel.demo.core.application.facade;

import com.hillel.demo.core.application.dto.AddUserRequestDto;
import com.hillel.demo.core.application.dto.PageDto;
import com.hillel.demo.core.application.dto.UserDto;
import com.hillel.demo.core.database.entity.Gender;
import com.hillel.demo.core.domain.model.User;
import com.hillel.demo.core.domain.service.UserService;
import com.hillel.demo.core.mapper.UserMapper;
import javassist.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserFacade {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserFacade(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    public PageDto<UserDto> findAll(int pageNum, int pageSize) {
        List<UserDto> userList = userService.findAll(pageNum, pageSize).getPageContent()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return new PageDto<>(userList, userList.size());
    }

    public UserDto getUser(Long id) throws NotFoundException {
        User user = userService.getUser(id);
        return userMapper.toDto(user);
    }

    public UserDto saveUser(AddUserRequestDto userDto) {
        User user = userMapper.dtoToModel(userDto);
        return userMapper.toDto(userService.saveUser(user));
    }

    public UserDto updateUser(Long id, AddUserRequestDto userDto) throws NotFoundException {
        User user = userMapper.dtoToModel(userDto);
        return userMapper.toDto(userService.updateUser(id, user));
    }

    public void deleteUser(Long id) throws NotFoundException {
        userService.deleteUser(id);
    }

    public PageDto<UserDto> findUsersByFirstNameAscending(int pageNum, int pageSize) {
        List<UserDto> userList = userService.findUsersByFirstNameAscending(pageNum, pageSize).getPageContent()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return new PageDto<>(userList, userList.size());
    }

    public PageDto<UserDto> findUsersByFirstNameDescending(int pageNum, int pageSize) {
        List<UserDto> userList = userService.findUsersByFirstNameDescending(pageNum, pageSize).getPageContent()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return new PageDto<>(userList, userList.size());
    }

    public PageDto<UserDto> findUsersByGender(Gender gender, int pageNum, int pageSize) {
        List<UserDto> userList = userService.findUsersByGender(gender, pageNum, pageSize).getPageContent()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return new PageDto<>(userList, userList.size());
    }

    public List<UserDto> findUsersByAgeAscending() {
        return userService.findUsersByAgeAscending()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> findUsersByAgeDescending() {
        return userService.findUsersByAgeDescending()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> findUsersByLastNameAscending() {
        return userService.findUsersByLastNameAscending()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> findUsersByLastNameDescending() {
        return userService.findUsersByLastNameDescending()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto findUserByEmail(String email) throws NotFoundException {
        User user = userService.findUserByEmail(email);
        return userMapper.toDto(user);
    }
}
