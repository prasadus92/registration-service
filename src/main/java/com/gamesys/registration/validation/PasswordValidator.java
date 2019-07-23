package com.gamesys.registration.validation;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        // Length should be at least 4, there should be at least 1 uppercase character and at least 1 number
        return StringUtils.hasLength(password) && password.length() >= 4 && !password.equals(password.toLowerCase()) && password.matches(".*\\d.*");
    }
}
