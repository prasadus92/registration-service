package com.gamesys.registration.validation;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static junit.framework.TestCase.assertTrue;

public class MinimumAgeValidatorTest {

    private MinimumAgeValidator minimumAgeValidator;

    @Before
    public void setup() {
        minimumAgeValidator = new MinimumAgeValidator();
    }

    @Test
    public void testValidationFailsForAgeLessThan18() {
        assertTrue(minimumAgeValidator.isNotValid(LocalDate.of(2019, 6, 10)));
    }

    @Test
    public void testValidationFailsForNullDob() {
        assertTrue(minimumAgeValidator.isNotValid(null));
    }
}
