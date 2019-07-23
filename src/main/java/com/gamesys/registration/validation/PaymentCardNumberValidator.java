package com.gamesys.registration.validation;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PaymentCardNumberValidator implements ConstraintValidator<PaymentCardNumber, String> {

    @Override
    public boolean isValid(String paymentCardNumber, ConstraintValidatorContext constraintValidatorContext) {
        // Length should be between 15 and 19 & payment Card Number should only contain numbers :)
        return StringUtils.hasLength(paymentCardNumber) && paymentCardNumber.length() >= 15 && paymentCardNumber.length() <= 19 &&
               paymentCardNumber.matches("[0-9]+");
    }
}
