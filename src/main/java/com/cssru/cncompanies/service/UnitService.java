package com.cssru.cncompanies.service;

import com.cssru.cncompanies.dto.UnitDto;
import com.cssru.cncompanies.exception.AccessDeniedException;

import java.util.List;

public interface UnitService {
    void add(UnitDto unitDto) throws AccessDeniedException;

    UnitDto get(Long id) throws AccessDeniedException;

    void update(UnitDto unitDto) throws AccessDeniedException;

    void delete(Long id) throws AccessDeniedException;

    List<UnitDto> listByCompany(Long companyId) throws AccessDeniedException;

    List<UnitDto> listByManager(Long managerId) throws AccessDeniedException;

    List<UnitDto> list();
}
