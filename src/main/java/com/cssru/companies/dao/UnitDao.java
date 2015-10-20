package com.cssru.companies.dao;

import com.cssru.companies.domain.Company;
import com.cssru.companies.domain.Unit;

import java.util.List;

public interface UnitDao {
    void create(Unit unit);
    List<Unit> list(Company company);
    Unit get(Long id);

    void update(Unit unit);

    void delete(Long id);
}
