package com.cssru.companies.service;

import com.cssru.companies.dto.CompanyDto;

import java.util.List;

public interface CompanyService {
    void create(CompanyDto company);

    CompanyDto get(Long id);

    List<CompanyDto> listByHolder(Long holderAccountId);

    void delete(Long id);

    void update(CompanyDto company);
}
