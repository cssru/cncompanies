package com.cssru.cncompanies.service;

import com.cssru.cncompanies.domain.Account;
import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.dto.AccountDto;
import com.cssru.cncompanies.exception.AccessDeniedException;

import java.util.List;

public interface AccountService {
	Account create(AccountDto accountDto);
	Account get(String userName) throws AccessDeniedException;
	Account get(Long id) throws AccessDeniedException;
	Account get(Human human) throws AccessDeniedException;
	void delete(Long id) throws AccessDeniedException;
	void removeExpiredAccounts(Long currentTime);
	void update(Account account, boolean passwordUpdate) throws AccessDeniedException;
	List<Account> list();
	Long getEmployeesCount(Account login);
}
