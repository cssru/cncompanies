package com.cssru.cncompanies.service;

import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.exception.AccessDeniedException;

import java.util.List;

public interface CompanyService {
	void add(Company company) throws AccessDeniedException;
	List<Company> list(Long ownerId) throws AccessDeniedException;
	void delete(Long id) throws AccessDeniedException;
	void update(Company company) throws AccessDeniedException;
	Company get(Long id) throws AccessDeniedException;
}
