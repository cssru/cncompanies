package com.cssru.companies.dao;

import com.cssru.companies.domain.Company;
import com.cssru.companies.domain.Employee;
import com.cssru.companies.domain.Unit;

import java.util.List;

public interface UnitDao {
    void add(Unit unit);

    List<Unit> list(Company company);

    List<Unit> list(Employee unitManager);

    List<Unit> listVisible(Employee manager);

    void delete(Unit unit);

    void update(Unit unit);

    Unit get(Long id);
}
