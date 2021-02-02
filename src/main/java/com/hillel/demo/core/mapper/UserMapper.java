package com.hillel.demo.core.mapper;

import com.hillel.demo.core.application.dto.AddUserRequestDto;
import com.hillel.demo.core.application.dto.UserDto;
import com.hillel.demo.core.database.entity.UserEntity;
import com.hillel.demo.core.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserMapper {

    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserMapper(ModelMapper mapper, PasswordEncoder passwordEncoder) {
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto toDto(User user) {
        return Objects.isNull(user) ? null : mapper.map(user, UserDto.class);
    }

    public User toModel(UserEntity userEntity) {
        return Objects.isNull(userEntity) ? null : mapper.map(userEntity, User.class);
    }

    public User dtoToModel(AddUserRequestDto userDto) {
        return Objects.isNull(userDto) ? null : mapper.map(userDto, User.class);
    }

    public UserEntity modelToEntity(User user) {
        UserEntity userEntity = mapper.map(user, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        return userEntity;
    }
}
