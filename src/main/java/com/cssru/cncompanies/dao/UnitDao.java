package com.cssru.cncompanies.dao;

import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Employee;
import com.cssru.cncompanies.domain.Unit;

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
