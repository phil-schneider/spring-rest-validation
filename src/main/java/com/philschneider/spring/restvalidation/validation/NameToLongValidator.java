package com.philschneider.spring.restvalidation.validation;


import com.philschneider.spring.restvalidation.dto.InputDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class NameToLongValidator implements ConstraintValidator<NameToLongConstraint, InputDto> {

    protected long maxLength = 0;


    @Override
    public boolean isValid(InputDto value, ConstraintValidatorContext context) {
        long length = StringUtils.length(value.getFirstName()) + StringUtils.length(value.getName());
        boolean result = length <= maxLength;

        if (!result) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Combination is to long. Allowed: " + maxLength + " current length: " + length)
                    .addConstraintViolation();
        }
        return result;
    }

    @Override
    public void initialize(NameToLongConstraint constraintAnnotation) {
        this.maxLength = constraintAnnotation.maxLength;
    }
}
