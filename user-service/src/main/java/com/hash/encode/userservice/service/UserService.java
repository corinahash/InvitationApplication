package com.hash.encode.userservice.service;

import com.hash.encode.userservice.dto.UserDto;
import com.hash.encode.userservice.model.User;
import com.hash.encode.userservice.repository.UserRepository;
import com.hash.encode.userservice.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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

    public User getUser(int id) {
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public User updateUser(int id, UserDto user) {
        User toBeUpdatedUser = userRepository.findById(id).orElseThrow(NoSuchElementException::new);
        toBeUpdatedUser.setBirthday(validateBirthday(user.getBirthday(), user.getBirthday()));
        toBeUpdatedUser.setEmail(UserValidator.validateEmail(user.getEmail()) ? user.getEmail() : toBeUpdatedUser.getEmail());
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
}
