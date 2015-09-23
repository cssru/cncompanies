package com.cssru.cncompanies.dao;

import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.domain.Login;

import java.util.List;

public interface LoginDAO {
	Login getLogin(String userName);
	Login getLogin(Long id);
	Login getLogin(Human human);
	void addLogin(Login login);
	void removeLogin(Login login);
	void removeExpiredLogins(Long currentTime);
	void updateLogin(Login login);
	Long getEmployeesCount(Login login);
	// ADMIN METHODS
	List<Login> admListUser();
}
