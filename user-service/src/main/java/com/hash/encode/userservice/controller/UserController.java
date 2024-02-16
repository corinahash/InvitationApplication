package com.hash.encode.userservice.controller;

import com.hash.encode.userservice.dto.CreateUserDto;
import com.hash.encode.userservice.dto.UserDto;
import com.hash.encode.userservice.mappers.UserMapper;
import com.hash.encode.userservice.model.User;
import com.hash.encode.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserDto saveUser(@Valid @RequestBody CreateUserDto dto) {
        User user = userService.createUser(UserMapper.mapCreateUserDtoToUser(dto));
        return UserMapper.mapUserToUserDto(user);
    }

    @GetMapping("/{id}")
    public UserDto getUser(int id) {
        return UserMapper.mapUserToUserDto(userService.getUser(id));
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers().stream().map(UserMapper::mapUserToUserDto).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void deleteUser(int id) {
        userService.deleteUser(id);
    }

    @PatchMapping("/{id}")
    public UserDto updateUser(int id, UserDto dto) {
        return UserMapper.mapUserToUserDto(userService.updateUser(id, dto));
    }
}
