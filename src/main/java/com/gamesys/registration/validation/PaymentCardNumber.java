package com.gamesys.registration.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target ({ElementType.METHOD, ElementType.FIELD})
@Retention (RetentionPolicy.RUNTIME)
@Constraint (validatedBy = PaymentCardNumberValidator.class)
public @interface PaymentCardNumber {

    String message() default "Payment Card Number should contain only digits and the lenth should be between 15 to 19";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
