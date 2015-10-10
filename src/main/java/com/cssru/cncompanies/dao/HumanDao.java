package com.cssru.cncompanies.dao;

import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Employee;
import com.cssru.cncompanies.domain.Unit;

import java.util.List;

public interface HumanDao {
    Employee get(Long id);

    void add(Employee employee);

    List<Employee> list(Unit unit);

    List<Employee> list(Company company);

    List<Employee> list(long lastModified, Employee manager);

    void delete(Long id);

    void delete(Employee employee);

    void deleteWithoutLogins();

    void update(Employee employee);
}
