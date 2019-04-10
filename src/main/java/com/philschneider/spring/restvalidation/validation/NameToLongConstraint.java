package com.philschneider.spring.restvalidation.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NameToLongValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NameToLongConstraint {

    /**
     * max length of firstname and name together
     */
    long maxLength = 9;

    String message() default "Combination name and firstname to long.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
