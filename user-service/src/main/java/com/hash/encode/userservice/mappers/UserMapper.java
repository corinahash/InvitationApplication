package com.hash.encode.userservice.mappers;

import com.hash.encode.userservice.dto.ChangePasswordDto;
import com.hash.encode.userservice.dto.CreateUserDto;
import com.hash.encode.userservice.dto.UserDto;
import com.hash.encode.userservice.model.Role;
import com.hash.encode.userservice.model.User;
import com.hash.encode.userservice.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class UserMapper {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static User mapCreateUserDtoToUser(CreateUserDto dto) {
        return User.builder()
                .email(dto.getEmail())
                .username(getUsernameFromEmail(dto.getEmail()))
                .password(passwordEncoder.encode(dto.getPassword()))
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .birthday(dto.getBirthday())
                .phoneNumber(UserValidator.validatePhoneNumber(dto.getPhoneNumber()))
                .role(Role.ADMIN)
                .build();
    }

    public static UserDto mapUserToUserDto(User user) {
        return new UserDto(user.getEmail(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getBirthday(), user.getPhoneNumber());
    }

    public static String getUsernameFromEmail(String email) {
        if (UserValidator.validateEmail(email)) {
            return email.subSequence(0, email.indexOf("@")).toString();
        } else {
            throw new UnsupportedOperationException("Incorrect email!");
        }
    }

    public static User validatePasswordAndUpdate(User user, ChangePasswordDto dto) {
        if (passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            if (dto.getNewPassword().equals(dto.getConfirmPassword())) {
                user.setPassword(dto.getNewPassword());
                return user;
            } else {
                throw new UnsupportedOperationException("The confirm password doesn't match)");
            }
        } else {
            throw new UnsupportedOperationException("Wrong old password!");
        }
    }
}
