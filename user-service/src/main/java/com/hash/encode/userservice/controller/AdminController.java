package com.hash.encode.userservice.controller;

import com.hash.encode.userservice.dto.UserDto;
import com.hash.encode.userservice.mappers.UserMapper;
import com.hash.encode.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get all users!")
    public List<UserDto> getAllUsers(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sorted") String sorted) {
        Pageable pageableRequest = PageRequest.of(page - 1, size, Sort.by(sorted));
        return userService.getAllUsers(pageableRequest)
                .getContent()
                .stream()
                .map(UserMapper::mapUserToUserDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user!")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }
}
