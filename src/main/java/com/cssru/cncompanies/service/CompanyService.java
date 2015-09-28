package com.cssru.cncompanies.service;

import com.cssru.cncompanies.domain.Account;
import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.dto.CompanyDto;
import com.cssru.cncompanies.exception.AccessDeniedException;

import java.util.List;

public interface CompanyService {
	void add(CompanyDto company) throws AccessDeniedException;
	List<CompanyDto> list(Account holder) throws AccessDeniedException;
	List<CompanyDto> list(Human manager) throws AccessDeniedException;
	void delete(Long id) throws AccessDeniedException;
	void update(CompanyDto company) throws AccessDeniedException;
	Company get(Long id) throws AccessDeniedException;
}
