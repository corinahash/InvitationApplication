package com.hash.encode.userservice.service;

import com.hash.encode.userservice.dto.ChangePasswordDto;
import com.hash.encode.userservice.dto.LoginUserDto;
import com.hash.encode.userservice.dto.PatchUserDto;
import com.hash.encode.userservice.mappers.UserMapper;
import com.hash.encode.userservice.model.User;
import com.hash.encode.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public User createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UnsupportedOperationException("The user already exists!");
        } else {
            if (userRepository.findByUsername(user.getUsername()).isPresent()) {
                String possibleUsername = user.getUsername() + new Random().nextInt(1000);
                //assure that username is unique
                while (userRepository.findByUsername(possibleUsername).isPresent()) {
                    possibleUsername = user.getUsername() + new Random().nextInt(1000);
                }
                user.setUsername(possibleUsername);
            }
            return userRepository.save(user);
        }
    }

    public User login(LoginUserDto user) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        return userRepository.findByUsername(user.getUsername()).orElseThrow(() -> new NoSuchElementException("User not found!"));
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public User updateUser(int id, PatchUserDto user) {
        User toBeUpdatedUser = userRepository.findById(id).orElseThrow(NoSuchElementException::new);
        toBeUpdatedUser.setBirthday(validateBirthday(user.getBirthday(), user.getBirthday()));
        toBeUpdatedUser.setFirstName(validateField(toBeUpdatedUser.getFirstName(), user.getFirstName()));
        toBeUpdatedUser.setLastName(validateField(toBeUpdatedUser.getLastName(), user.getLastName()));
        toBeUpdatedUser.setPhoneNumber(validateField(toBeUpdatedUser.getPhoneNumber(), user.getPhoneNumber()));
        return userRepository.save(toBeUpdatedUser);
    }

    private String validateField(String originalValue, String changedValue) {
        if (changedValue != null && !changedValue.isEmpty()) {
            return changedValue;
        } else {
            return originalValue;
        }
    }

    private LocalDate validateBirthday(LocalDate originalBirthday, LocalDate changedBirthday) {
        if (changedBirthday != null && !changedBirthday.isBefore(LocalDate.now())) {
            return changedBirthday;
        } else {
            return originalBirthday;
        }
    }

    public User updatePassword(User user, ChangePasswordDto dto) {
        return userRepository.save(UserMapper.validatePasswordAndUpdate(user, dto));
    }
}
