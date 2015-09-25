package com.cssru.cncompanies.dao;

import com.cssru.cncompanies.domain.Account;
import com.cssru.cncompanies.domain.Human;

import java.util.List;

public interface AccountDAO {
	Account get(String login);
	Account get(Long id);
	Account get(Human human);
	void add(Account account);
	void delete(Long id);
	void update(Account account);
	List<Account> list();
	Long getEmployeesCount(Account account);
}
