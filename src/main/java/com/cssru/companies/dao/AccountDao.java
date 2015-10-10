package com.cssru.companies.dao;

import com.cssru.companies.domain.Account;
import com.cssru.companies.domain.Employee;

import java.util.Date;
import java.util.List;

public interface AccountDao {
    void create(Account account);

    Account get(String login);

    Account get(Long id);

    Account get(Employee employee);

    void update(Account account);

    void delete(Long id);

    List<Account> list();

    List<Account> getExpired(Date date);

    Long getEmployeesCount(Account account);
}
