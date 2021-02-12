package com.hillel.demo.core.domain.service;

import com.hillel.demo.client.someapi.SomeClient;
import com.hillel.demo.core.application.dto.AddUserRequestDto;
import com.hillel.demo.core.application.dto.PageDto;
import com.hillel.demo.core.database.entity.Gender;
import com.hillel.demo.core.database.entity.UserEntity;
import com.hillel.demo.core.database.repository.UserRepository;
import com.hillel.demo.core.domain.model.User;
import com.hillel.demo.core.mapper.UserMapper;
import javassist.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final SomeClient someClient;

    public UserService(UserRepository userRepository, UserMapper userMapper, SomeClient someClient) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.someClient = someClient;
    }

    public void singUpNewUser(AddUserRequestDto userDto) {
        User user = userMapper.dtoToModel(userDto);
        userMapper.toDto(saveUser(user));
    }

    public PageDto<User> findAll(Integer pageNum, Integer pageSize){
        // return someClient.getAllUsers();
        List<User> userList = StreamSupport
                .stream(userRepository.findAll(PageRequest.of(pageNum, pageSize)).spliterator(), false)
                .map(userMapper::toModel)
                .collect(Collectors.toList());
        return new PageDto<>(userList, userList.size());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(""));
        return userMapper.toModel(userEntity);
    }

    public User getUser(Long id) throws NotFoundException {
        return userRepository.findById(id).map(userMapper::toModel)
                .orElseThrow(() -> new NotFoundException(String.format("User not found with id: " + id)));
    }

    public User saveUser(User user) {
        UserEntity userEntity = userMapper.modelToEntity(user);
        return userMapper.toModel(userRepository.save(userEntity));
    }

    public User updateUser(Long id, User user) throws NotFoundException {
        userRepository.findById(id).map(userMapper::toModel)
                .orElseThrow(() -> new NotFoundException(String.format("User not found with id: " + id)));
        UserEntity userEntity = userMapper.modelToEntity(user);
        userEntity.setId(id);
        return userMapper.toModel(userRepository.save(userEntity));
    }

    public void deleteUser(Long id) throws NotFoundException {
        userRepository.findById(id).map(userMapper::toModel)
                .orElseThrow(() -> new NotFoundException(String.format("User not found with id: " + id)));
        userRepository.deleteById(id);
    }

    public PageDto<User> findUsersByFirstNameAscending(Integer pageNum, Integer pageSize) {
        List<User> userList = StreamSupport
                .stream(userRepository.findAllByOrderByFirstNameAsc(PageRequest.of(pageNum, pageSize)).spliterator(), false)
                .map(userMapper::toModel).collect(Collectors.toList());
        return new PageDto<>(userList, userList.size());
    }

    public PageDto<User> findUsersByFirstNameDescending(Integer pageNum, Integer pageSize) {
        List<User> userList = StreamSupport
                .stream(userRepository.findAllByOrderByFirstNameDesc(PageRequest.of(pageNum, pageSize)).spliterator(), false)
                .map(userMapper::toModel).collect(Collectors.toList());
        return new PageDto<>(userList, userList.size());
    }

    public PageDto<User> findUsersByGender(Gender gender, Integer pageNum, Integer pageSize) {
        List<User> userList = StreamSupport
                .stream(userRepository.findAllByGender(gender, PageRequest.of(pageNum, pageSize)).spliterator(), false)
                .map(userMapper::toModel)
                .collect(Collectors.toList());
        return new PageDto<>(userList, userList.size());
    }

    public List<User> findUsersByAgeAscending() {
        return StreamSupport.stream(userRepository.findAllByOrderByAgeAsc().spliterator(), false)
                .map(userMapper::toModel)
                .collect(Collectors.toList());
    }

    public List<User> findUsersByAgeDescending() {
        return StreamSupport.stream(userRepository.findAllByOrderByAgeDesc().spliterator(), false)
                .map(userMapper::toModel)
                .collect(Collectors.toList());
    }

    public List<User> findUsersByLastNameAscending() {
        return StreamSupport.stream(userRepository.findAllByOrderByLastNameAsc().spliterator(), false)
                .map(userMapper::toModel)
                .collect(Collectors.toList());
    }

    public List<User> findUsersByLastNameDescending() {
        return StreamSupport.stream(userRepository.findAllByOrderByLastNameDesc().spliterator(), false)
                .map(userMapper::toModel)
                .collect(Collectors.toList());
    }

    public User findUserByEmail(String email) throws NotFoundException {
        return userRepository.findByEmail(email).map(userMapper::toModel)
                .orElseThrow(() -> new NotFoundException(String.format("User not found with email: " + email)));
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
