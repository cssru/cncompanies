package com.cssru.companies.service;

import com.cssru.companies.domain.Company;
import com.cssru.companies.dto.CompanyDto;

import java.util.List;

public interface CompanyService {
    void add(CompanyDto company) throws AccessDeniedException;

    List<CompanyDto> list() throws AccessDeniedException;

    List<CompanyDto> list(Long managerId) throws AccessDeniedException;

    void delete(Long id) throws AccessDeniedException;

    void update(CompanyDto company) throws AccessDeniedException;

    Company get(Long id) throws AccessDeniedException;
}
