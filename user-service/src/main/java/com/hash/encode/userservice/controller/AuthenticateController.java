package com.hash.encode.userservice.controller;

import com.hash.encode.userservice.auth.AuthenticationResponse;
import com.hash.encode.userservice.config.JwtService;
import com.hash.encode.userservice.dto.CreateUserDto;
import com.hash.encode.userservice.dto.LoginUserDto;
import com.hash.encode.userservice.mappers.UserMapper;
import com.hash.encode.userservice.model.User;
import com.hash.encode.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticateController {
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody CreateUserDto request) {
        User user = userService.createUser(UserMapper.mapCreateUserDtoToUser(request));
        String jwtToken = jwtService.generateToken(user);
        return ResponseEntity.ok(AuthenticationResponse.builder()
                .token(jwtToken)
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginUserDto request) {
        User user = userService.login(request);
        String jwtToken = jwtService.generateToken(user);
        return ResponseEntity.ok(AuthenticationResponse.builder()
                .token(jwtToken)
                .build());
    }
}
