package com.hillel.demo.core.application.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserAgeValidator implements ConstraintValidator<UserAgeConstraint, Integer> {

    @Override
    public boolean isValid(Integer userAge, ConstraintValidatorContext context) {
        return userAge % 2 == 0;
    }
}
