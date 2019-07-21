package com.javatpoint.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.javatpoint.dto.UserDto;


public class PasswordsEqualConstraintValidator implements
    ConstraintValidator<PasswordsEqualConstraint, Object> {

@Override
public void initialize(PasswordsEqualConstraint arg0) {
}

@Override
public boolean isValid(Object candidate, ConstraintValidatorContext arg1) {
    UserDto user = (UserDto) candidate;
    return user.getPassword().equals(user.getMatchingPassword());
}
}
