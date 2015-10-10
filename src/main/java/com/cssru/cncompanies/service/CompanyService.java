package com.cssru.cncompanies.service;

import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.dto.CompanyDto;
import com.cssru.cncompanies.exception.AccessDeniedException;

import java.util.List;

public interface CompanyService {
    void add(CompanyDto company) throws AccessDeniedException;

    List<CompanyDto> list() throws AccessDeniedException;

    List<CompanyDto> list(Long managerId) throws AccessDeniedException;

    void delete(Long id) throws AccessDeniedException;

    void update(CompanyDto company) throws AccessDeniedException;

    Company get(Long id) throws AccessDeniedException;
}
