package com.hash.encode.userservice.controller;

import com.hash.encode.userservice.dto.UserDto;
import com.hash.encode.userservice.mappers.UserMapper;
import com.hash.encode.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    @Operation(summary = "Get an user!")
    public UserDto getUser(@PathVariable int id) {
        return UserMapper.mapUserToUserDto(userService.getUser(id));
    }

    @GetMapping
    @Operation(summary = "Get all users!")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers().stream().map(UserMapper::mapUserToUserDto).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an user!")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update an user!")
    public UserDto updateUser(@PathVariable int id, @RequestBody UserDto dto) {
        return UserMapper.mapUserToUserDto(userService.updateUser(id, dto));
    }
}
