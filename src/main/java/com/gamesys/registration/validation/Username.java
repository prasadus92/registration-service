package com.gamesys.registration.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target ({ElementType.METHOD, ElementType.FIELD})
@Retention (RetentionPolicy.RUNTIME)
@Constraint (validatedBy = UsernameValidator.class)
public @interface Username {

    String message() default "Username should be without any spaces and should only contain alphanumeric characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
