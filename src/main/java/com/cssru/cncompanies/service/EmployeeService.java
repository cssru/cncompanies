package com.cssru.cncompanies.service;

import com.cssru.cncompanies.dto.EmployeeDto;

public interface EmployeeService {
    void create(Long postId, EmployeeDto employeeDto);

    EmployeeDto get(Long id);

    void update(EmployeeDto employeeDto);

    void delete(Long employeeId);
}
