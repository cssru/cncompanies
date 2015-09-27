package com.cssru.cncompanies.dao;

import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Human;

import java.util.List;

public interface CompanyDAO {
	void save(Company company);
	List<Company> list(Human owner);
	void delete(Company company);
	void update(Company company);
	Company get(Long id);
}
