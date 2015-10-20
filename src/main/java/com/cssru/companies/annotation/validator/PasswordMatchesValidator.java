package com.cssru.companies.annotation.validator;

import com.cssru.companies.annotation.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches arg0) {
    }

    @Override
    public boolean isValid(Object accountObject, ConstraintValidatorContext context) {
        AccountRegisterDto accountDto = (AccountRegisterDto) accountObject;
        return accountDto.getPassword().equals(accountDto.getPasswordConfirm());
    }

}
