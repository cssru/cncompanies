package com.cssru.cncompanies.dao;

import java.util.List;

import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.domain.Login;

public interface CompanyDAO {
	void add(Company company);
	List<Company> list(Human owner);
	void delete(Company company);
	void update(Company company);
	Company get(Long id);
}
