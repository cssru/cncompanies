package com.cssru.cncompanies.annotation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cssru.cncompanies.annotation.PasswordMatches;
import com.cssru.cncompanies.dto.AccountRegisterDto;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

	@Override
	public void initialize(PasswordMatches arg0) {
	}

	@Override
	public boolean isValid(Object accountObject, ConstraintValidatorContext context) {
		AccountRegisterDto accountDto = (AccountRegisterDto)accountObject;
		return accountDto.getPassword().equals(accountDto.getPasswordConfirm());
	}

}
