package com.cssru.companies.service;

import com.cssru.companies.domain.Account;
import com.cssru.companies.dto.AccountDto;
import com.cssru.companies.dto.ChangePasswordDto;
import com.cssru.companies.exception.PasswordChangeException;

import java.util.List;

public interface AccountService {
    Account create(AccountDto accountDto);

    AccountDto get(String userName);

    AccountDto get(Long id);

    List<AccountDto> list();

    void update(AccountDto accountDto);

    void changePassword(ChangePasswordDto chagePasswordDto) throws PasswordChangeException;

    void delete(Long id);
}
