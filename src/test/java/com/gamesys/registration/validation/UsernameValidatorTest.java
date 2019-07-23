package com.gamesys.registration.validation;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class UsernameValidatorTest {

    private UsernameValidator usernameValidator;

    @Before
    public void setup() {
        usernameValidator = new UsernameValidator();
    }

    @Test
    public void testValidationFailsForNullOrEmptyString() {
        assertFalse(usernameValidator.isValid("", null));
        assertFalse(usernameValidator.isValid(null, null));
    }

    @Test
    public void testValidationFailsForNonAlphanumericChars() {
        assertFalse(usernameValidator.isValid("BruceWayne_*", null));
        assertFalse(usernameValidator.isValid("Bruce++&*", null));
    }
}
