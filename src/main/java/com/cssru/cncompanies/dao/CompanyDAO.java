package com.cssru.cncompanies.dao;

import java.util.List;

import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.domain.Login;

public interface CompanyDAO {
	void addCompany(Company company);
	List<Company> listCompany(Human owner);
	void removeCompany(Company company);
	void updateCompany(Company company);
	Company getCompany(Long id);
}
