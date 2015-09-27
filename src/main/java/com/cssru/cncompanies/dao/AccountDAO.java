package com.cssru.cncompanies.dao;

import com.cssru.cncompanies.domain.Account;
import com.cssru.cncompanies.domain.Human;

import java.util.Date;
import java.util.List;

public interface AccountDAO {
	void create(Account account);
	Account get(String login);
	Account get(Long id);
	Account get(Human human);
	void update(Account account);
	void delete(Long id);
	List<Account> list();
	List<Account> getExpired(Date date);
	Long getEmployeesCount(Account account);
}
