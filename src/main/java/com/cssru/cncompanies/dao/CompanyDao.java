package com.cssru.cncompanies.dao;

import com.cssru.cncompanies.domain.Account;
import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Employee;

import java.util.List;

public interface CompanyDao {
    void save(Company company);

    List<Company> listByManager(Employee manager);

    List<Company> listByHolder(Account holder);

    void delete(Company company);

    void update(Company company);

    Company get(Long id);
}
