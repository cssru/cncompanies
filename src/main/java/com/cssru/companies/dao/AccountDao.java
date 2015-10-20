package com.cssru.companies.dao;

import com.cssru.companies.domain.Account;

import java.util.List;

public interface AccountDao {
    void create(Account account);
    Account get(String login);
    Account get(Long id);

    List<Account> list();
    void update(Account account);
    void delete(Long id);
}
