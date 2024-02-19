package com.hash.encode.userservice.mappers;

import com.hash.encode.userservice.dto.CreateUserDto;
import com.hash.encode.userservice.dto.UserDto;
import com.hash.encode.userservice.model.User;
import com.hash.encode.userservice.validator.UserValidator;
import org.mindrot.jbcrypt.BCrypt;

public class UserMapper {

    public static User mapCreateUserDtoToUser(CreateUserDto dto) {
        return User.builder()
                .email(dto.getEmail())
                .username(getUsernameFromEmail(dto.getEmail()))
                .password(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()))
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .birthday(dto.getBirthday())
                .phoneNumber(UserValidator.validatePhoneNumber(dto.getPhoneNumber()))
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
}
