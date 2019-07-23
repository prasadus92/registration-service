package com.gamesys.registration.validation;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class IssuerIdNumberValidatorTest {

    private IssuerIdNumberValidator issuerIdNumberValidator;

    @Before
    public void setup() {
        issuerIdNumberValidator = new IssuerIdNumberValidator();
    }

    @Test
    public void testValidationFailsForNullOrEmptyString() {
        assertFalse(issuerIdNumberValidator.isValid(""));
        assertFalse(issuerIdNumberValidator.isValid(null));
    }

    @Test
    public void testValidationFailsForInvalidValues() {
        // Digits > 6
        assertFalse(issuerIdNumberValidator.isValid("1234567"));
        // Digits < 6
        assertFalse(issuerIdNumberValidator.isValid("123"));
        // Digits with different chars
        assertFalse(issuerIdNumberValidator.isValid("abc124"));
    }
}
