package com.cssru.cncompanies.service.impl;

import com.cssru.cncompanies.dao.AccountDao;
import com.cssru.cncompanies.dao.EmployeeDao;
import com.cssru.cncompanies.domain.Account;
import com.cssru.cncompanies.domain.Employee;
import com.cssru.cncompanies.domain.Login;
import com.cssru.cncompanies.domain.Unit;
import com.cssru.cncompanies.dto.AccountRegisterDto;
import com.cssru.cncompanies.service.AccountService;
import com.cssru.cncompanies.service.CompanyService;
import com.cssru.cncompanies.service.EmployeeService;
import com.cssru.cncompanies.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AccountDao accountDAO;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Account create(AccountRegisterDto accountDto) {
        return create(accountDto, null);
    }

    @Transactional
    @Override
    public Account create(AccountRegisterDto accountDto, Unit unit) {
        Account account = new Account();
        account.setLogin(accountDto.getLogin());
        account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        account.setEmail(accountDto.getEmail());

        Employee employee = new Employee();
        employee.setSurname(accountDto.getSurname());
        employee.setName(accountDto.getName());
        employee.setLastname(accountDto.getLastname());
        employee.setNote(accountDto.getNote());
        employee.setBirthday(accountDto.getBirthday());

        if (unit != null) {
            employee.setUnit(unit);
        }

        account.setEmployee(employee);
        employeeDao.add(employee);
        accountDAO.add(account);

        return account;
    }

    @Transactional(readOnly = true)
    @Override
    public Account get(String userName) {
        return accountDAO.get(userName);
    }

    @Transactional(readOnly = true)
    @Override
    public Account get(Long id) {
        return accountDAO.get(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Account get(Employee employee) {
        return accountDAO.get(employee);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        accountDAO.delete(id);
    }

    @Transactional
    @Override
    public void removeExpired(Date now) {
        List<Account> expired = accountDAO.getExpired(now);
        for (Account account : expired) {
            accountDAO.delete(account.getId());
        }
    }

    @Transactional
    @Override
    public void updateLogin(Login login, boolean passwordUpdate) throws AccessDeniedException {
        if (passwordUpdate) {
            login.setPassword(passwordEncoder.encode(login.getPassword()));
        }
        employeeService.updateHuman(login.getHuman(), login);
        loginDAO.updateLogin(login);
    }


    @Transactional(readOnly = true)
    @Override
    public List<Login> admListUser() {
        return loginDAO.admListUser();
    }

    @Transactional(readOnly = true)
    @Override
    public Long getEmployeesCount(Login login) {
        return loginDAO.getEmployeesCount(login);
    }

}
