package com.cssru.companies.service.impl;

import com.cssru.companies.dao.AccountDao;
import com.cssru.companies.domain.Account;
import com.cssru.companies.dto.AccountDto;
import com.cssru.companies.dto.ChangePasswordDto;
import com.cssru.companies.exception.PasswordChangeException;
import com.cssru.companies.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Account create(AccountDto accountDto) {
        Account account = new Account();

        accountDto.mapTo(account);
        account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        accountDao.create(account);

        return account;
    }

    @Transactional(readOnly = true)
    @Override
    public AccountDto get(String userName) {
        Account account = accountDao.get(userName);
        AccountDto accountDto = new AccountDto();

        if (account != null) {
            accountDto.mapFrom(account);
        }

        return accountDto;
    }

    @Transactional(readOnly = true)
    @Override
    public AccountDto get(Long id) {
        Account account = accountDao.get(id);
        AccountDto accountDto = new AccountDto();

        if (account != null) {
            accountDto.mapFrom(account);
        }

        return accountDto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<AccountDto> list() {
        List<Account> accountList = accountDao.list();
        List<AccountDto> accountDtoList = new ArrayList<>(accountList.size());

        for (Account nextAccount : accountList) {
            AccountDto nextAccountDto = new AccountDto();
            nextAccountDto.mapFrom(nextAccount);
            accountDtoList.add(nextAccountDto);
        }

        return accountDtoList;
    }

    @Transactional
    @Override
    public void update(AccountDto accountDto) {
        Account account = accountDao.get(accountDto.getId());

        if (account != null) {
            accountDto.mapTo(account);
            accountDao.update(account);
        }
    }

    @Transactional
    @Override
    public void changePassword(ChangePasswordDto changePasswordDto) throws PasswordChangeException {
        Account account = accountDao.get(changePasswordDto.getLoginName());

        if (account != null) {
            if (passwordEncoder.matches(changePasswordDto.getOldPassword(), account.getPassword())) {
                account.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
                accountDao.update(account);
            } else {
                throw new PasswordChangeException("Incorrect old password");
            }
        }
    }

    @Transactional
    @Override
    public void delete(Long id) {
        accountDao.delete(id);
    }

}
