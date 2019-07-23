package com.gamesys.registration.validation;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class PaymentCardNumberValidatorTest {

    private PaymentCardNumberValidator paymentCardNumberValidator;

    @Before
    public void setup() {
        paymentCardNumberValidator = new PaymentCardNumberValidator();
    }

    @Test
    public void testValidationFailsForNullOrEmptyString() {
        assertFalse(paymentCardNumberValidator.isValid("", null));
        assertFalse(paymentCardNumberValidator.isValid(null, null));
    }

    @Test
    public void testValidationFailsForInvalidLength() {
        // < 15
        assertFalse(paymentCardNumberValidator.isValid("81234567891234", null));
        // > 19
        assertFalse(paymentCardNumberValidator.isValid("81234567891234567890", null));
    }

    @Test
    public void testValidationFailsForNonDigits() {
        assertFalse(paymentCardNumberValidator.isValid("81234567891234bla", null));
    }
}
