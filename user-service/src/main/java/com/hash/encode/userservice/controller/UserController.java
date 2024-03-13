package com.hash.encode.userservice.controller;

import com.hash.encode.userservice.dto.ChangePasswordDto;
import com.hash.encode.userservice.dto.PatchUserDto;
import com.hash.encode.userservice.dto.UserDto;
import com.hash.encode.userservice.mappers.UserMapper;
import com.hash.encode.userservice.model.User;
import com.hash.encode.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get user info!")
    public UserDto getUser() {

        return UserMapper.mapUserToUserDto(getConnectedUser());
    }

    @DeleteMapping
    @Operation(summary = "Delete user!")
    public void deleteUser() {
        userService.deleteUser(getConnectedUser().getId());
    }

    @PatchMapping
    @Operation(summary = "Update user info!")
    public UserDto updateUser(@RequestBody PatchUserDto dto) {
        return UserMapper.mapUserToUserDto(userService.updateUser(getConnectedUser().getId(), dto));
    }

    @PatchMapping("/update-password")
    @Operation(summary = "Update password info!")
    public UserDto updatePassword(@RequestBody ChangePasswordDto dto) {
        return UserMapper.mapUserToUserDto(userService.updatePassword(getConnectedUser(), dto));
    }

    private User getConnectedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }
}
