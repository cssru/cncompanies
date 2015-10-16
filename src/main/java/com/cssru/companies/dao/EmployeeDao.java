package com.cssru.companies.dao;

import com.cssru.companies.domain.Employee;

public interface EmployeeDao {
    void create(Employee employee);
    Employee get(Long id);
    Employee get(String login);
    void update(Employee employee);

    void delete(Long id);
}
