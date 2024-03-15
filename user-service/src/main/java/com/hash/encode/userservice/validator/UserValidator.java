package com.hash.encode.userservice.validator;

import java.util.NoSuchElementException;
import java.util.regex.Pattern;

public class UserValidator {

    private static final String EMAIL_REGEX_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_NUMBER_REGEX_PATTERN = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$";

    public static boolean validateEmail(String email) {
        return Pattern.compile(EMAIL_REGEX_PATTERN)
                .matcher(email)
                .matches();
    }

    public static String validatePhoneNumber(String phoneNumber) {
        if (Pattern.compile(PHONE_NUMBER_REGEX_PATTERN)
                .matcher(phoneNumber)
                .matches()) {
            return phoneNumber;
        } else {
            throw new NoSuchElementException("Wrong phone number format!");
        }
    }
}
