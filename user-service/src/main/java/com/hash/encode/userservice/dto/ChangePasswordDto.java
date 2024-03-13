package com.hash.encode.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class ChangePasswordDto {

    String oldPassword;
    String newPassword;
    String confirmPassword;
}
