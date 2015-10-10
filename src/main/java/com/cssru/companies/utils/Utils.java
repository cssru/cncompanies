package com.cssru.companies.utils;

import com.cssru.companies.dao.AccountDao;
import com.cssru.companies.domain.Account;
import com.cssru.companies.domain.Employee;
import com.cssru.companies.secure.Role;
import org.springframework.security.core.context.SecurityContextHolder;

public class Utils {
    private Utils() {
    }

    public static boolean clientHasRoles(Role[] roles) {
        for (Role role : roles) {
            if (!SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getAuthorities()
                    .contains(new HumanGrantedAuthority(role))) {
                return false;
            }
        }
        return true;
    }

    public static boolean clientHasAnyRole(Role[] roles) {
        for (Role role : roles) {
            if (SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getAuthorities()
                    .contains(new HumanGrantedAuthority(role))) {
                return true;
            }
        }
        return false;
    }

    public static boolean clientHasRole(Role role) {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .contains(new HumanGrantedAuthority(role));
    }

    public static Account clientAccount(AccountDao dao) {
        return dao.get(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
    }

    public static String humanName(Employee employee) {
        return (employee == null) ? "" :
                new StringBuilder(employee.getSurname())
                        .append(" ")
                        .append(employee.getName().charAt(0))
                        .append(".")
                        .append(employee.getLastname().charAt(0))
                        .append(".").toString();
    }

}
