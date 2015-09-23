package com.cssru.cncompanies.service;

import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.domain.Login;
import com.cssru.cncompanies.domain.Unit;
import com.cssru.cncompanies.dto.AccountDto;
import com.cssru.cncompanies.exception.AccessDeniedException;

import java.util.List;

public interface LoginService {
	Login createLogin(AccountDto accountDto);
	Login createLogin(AccountDto accountDto, Unit unit);
	Login getLogin(String userName);
	Login getLogin(Long id);
	Login getLogin(Human human);
	void removeLogin(Login login);
	void removeExpiredLogins(Long currentTime);
	void removeUser(Login login) throws AccessDeniedException; // removes login, attached human and all companies and units, created by this user
	void updateLogin(Login login, boolean passwordUpdate) throws AccessDeniedException;
	Long getEmployeesCount(Login login);
	// ADMIN METHODS
	List<Login> admListUser();
}
