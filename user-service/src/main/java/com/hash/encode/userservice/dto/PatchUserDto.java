package com.hash.encode.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class PatchUserDto {

    private String username;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String phoneNumber;

}
