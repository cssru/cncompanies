package com.cssru.companies.service.impl;

import com.cssru.companies.dao.AccountDao;
import com.cssru.companies.dao.EmployeeDao;
import com.cssru.companies.domain.Account;
import com.cssru.companies.domain.Employee;
import com.cssru.companies.secure.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    AccountDao accountDao;

    @Autowired
    EmployeeDao employeeDao;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        Account account = accountDao.get(loginName);
        if (account == null) {
            Employee employee = employeeDao.get(loginName);
            if (employee == null) {
                throw new UsernameNotFoundException("User " + loginName + " not exists");
            } else {
                return createUserFromEmployee(employee);
            }
        } else {
            return createUserFromAccount(account);
        }
    }

    private UserDetails createUserFromEmployee(Employee employee) {
        return new User(employee.getLogin(),
                employee.getPassword(),
                Arrays.asList(new SimpleGrantedAuthority(employee.getPost().getRole().name())));
    }

    private UserDetails createUserFromAccount(Account account) {
        return account.getLogin().equalsIgnoreCase("admin")
                ?
                new User(account.getLogin(),
                        account.getPassword(),
                        Arrays.asList(new SimpleGrantedAuthority(Role.ROLE_ADMIN.name())))
                :
                new User(account.getLogin(),
                        account.getPassword(),
                        Arrays.asList(new SimpleGrantedAuthority(Role.ROLE_ACCOUNT_HOLDER.name())));
    }

}
