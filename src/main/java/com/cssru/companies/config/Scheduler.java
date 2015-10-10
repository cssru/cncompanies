package com.cssru.companies.config;

import com.cssru.companies.service.AccountService;
import com.cssru.companies.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {
    public static final long ONE_DAY = 1000 * 60 * 60 * 24;

    @Autowired
    private AccountService accountService;

    @Autowired
    private EmployeeService employeeService;

    public Scheduler() {
    }

    @Scheduled(fixedDelay = ONE_DAY)
    public void deleteExpiredAccounts() {
        accountService.removeExpiredLogins(System.currentTimeMillis());
    }
}
