package com.hash.encode.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDate;

@AllArgsConstructor
@Value
public class CreateUserDto {

    @NotBlank
    String email;

    @NotBlank
    String password;

    String firstName;
    String lastName;
    LocalDate birthday;
    String phoneNumber;
}
