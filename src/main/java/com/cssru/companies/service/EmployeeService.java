package com.cssru.companies.service;

import com.cssru.companies.dto.EmployeeDto;

public interface EmployeeService {
    void create(Long postId, EmployeeDto employeeDto);

    EmployeeDto get(Long id);

    void update(EmployeeDto employeeDto);

    void delete(Long employeeId);
}
