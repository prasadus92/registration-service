package com.gamesys.registration.validation;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class PasswordValidatorTest {

    private PasswordValidator passwordValidator;

    @Before
    public void setup() {
        passwordValidator = new PasswordValidator();
    }

    @Test
    public void testValidationFailsForLengthLessThan4() {
        assertFalse(passwordValidator.isValid("abc", null));
    }

    @Test
    public void testValidationFailsForNoUppercaseLetter() {
        assertFalse(passwordValidator.isValid("abcd", null));
    }

    @Test
    public void testValidationFailsForNoDigit() {
        assertFalse(passwordValidator.isValid("Abcd", null));
    }

    @Test
    public void testValidationFailsForNullOrEmptyString() {
        assertFalse(passwordValidator.isValid("", null));
        assertFalse(passwordValidator.isValid(null, null));
    }
}
