package com.cssru.cncompanies.service;

import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Login;
import com.cssru.cncompanies.exception.AccessDeniedException;

import java.util.List;

public interface CompanyService {
	void addCompany(Company company, Login managerLogin);
	List<Company> listCompany(Login managerLogin);
	void removeCompany(Long id, Login managerLogin) throws AccessDeniedException;
	void updateCompany(Company company, Login managerLogin) throws AccessDeniedException;
	Company getCompany(Long id, Login managerLogin) throws AccessDeniedException;
}
