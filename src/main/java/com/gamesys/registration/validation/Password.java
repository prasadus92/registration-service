package com.gamesys.registration.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target ({ElementType.METHOD, ElementType.FIELD})
@Retention (RetentionPolicy.RUNTIME)
@Constraint (validatedBy = PasswordValidator.class)
public @interface Password {

    String message() default "Minimum password length is 4 characters, there should be atleast 1 uppercase letter and 1 number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
