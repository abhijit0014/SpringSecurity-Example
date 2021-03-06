package com.spring.security.customAnotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.hibernate.validator.constraints.Email;

@Constraint(validatedBy=UniqueEmailValidator.class)
@Email
@Documented
@Retention(RUNTIME)
@Target(FIELD)
public @interface UniqueEmail {
    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
