package com.hillel.demo.core.domain.service;

import com.hillel.demo.core.database.entity.Gender;
import com.hillel.demo.core.database.entity.UserEntity;
import com.hillel.demo.core.database.repository.UserRepository;
import com.hillel.demo.core.domain.model.User;
import com.hillel.demo.core.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Test
    void loadUserByUsername() {
        UserEntity userEntity = new UserEntity();
        User user = new User();
        user.setId(19L);
        user.setGender(Gender.MALE);
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setEmail("test@gmail.com");
        user.setPassword("Test123");
        user.setAge(24);
        user.setBankAccounts(new ArrayList<>());
        user.setHospitalEntity(null);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userEntity));
        when(userMapper.toModel(any())).thenReturn(user);

        UserDetails userDetails = userService.loadUserByUsername("test@gmail.com");

        assertEquals("test@gmail.com", userDetails.getUsername());

        verify(userRepository, times(1)).findByEmail(anyString());
        verifyNoMoreInteractions(userRepository);

        verify(userMapper, times(1)).toModel(any());
        verifyNoMoreInteractions(userMapper);
    }

    @Test
    void loadUserByUsernameThrowsException() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("null@gmail.com"));
    }
}
