package com.cssru.companies.dao;

import com.cssru.companies.domain.Account;
import com.cssru.companies.domain.Company;

import java.util.List;

public interface CompanyDao {
    void create(Company company);
    Company get(Long id);

    List<Company> list(Account holder);

    void update(Company company);

    void delete(Long id);
}
