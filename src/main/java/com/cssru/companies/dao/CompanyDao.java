package com.cssru.companies.dao;

import com.cssru.companies.domain.Account;
import com.cssru.companies.domain.Company;
import com.cssru.companies.domain.Employee;

import java.util.List;

public interface CompanyDao {
    void save(Company company);

    List<Company> listByManager(Employee manager);

    List<Company> listByHolder(Account holder);

    void delete(Company company);

    void update(Company company);

    Company get(Long id);
}
