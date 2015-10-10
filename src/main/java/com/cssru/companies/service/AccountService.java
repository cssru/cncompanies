package com.cssru.companies.service;

import com.cssru.companies.domain.Account;
import com.cssru.companies.domain.Employee;
import com.cssru.companies.domain.Unit;
import com.cssru.companies.dto.AccountRegisterDto;

import java.util.Date;
import java.util.List;

public interface AccountService {
    Account create(AccountRegisterDto accountDto);

    Account create(AccountRegisterDto accountDto, Unit unit);

    Account get(String userName);

    Account get(Long id) throws AccessDeniedException;

    Account get(Employee employee) throws AccessDeniedException;

    void delete(Long id) throws AccessDeniedException;

    void removeExpired(Date now);

    void update(Account account, boolean passwordUpdate) throws AccessDeniedException;

    List<Account> list();

    Long getEmployeesCount(Account login);
}
