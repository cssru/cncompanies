package com.cssru.companies.service.impl;

import com.cssru.companies.dao.EmployeeDao;
import com.cssru.companies.domain.Employee;
import com.cssru.companies.domain.EmployeeMetadataElement;
import com.cssru.companies.dto.EmployeeDto;
import com.cssru.companies.dto.EmployeeMetadataElementDto;
import com.cssru.companies.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void create(Long postId, EmployeeDto employeeDto) {
        Employee employee = new Employee();
        // encoded password will not mapped
        employeeDto.mapTo(employee);
        // take password from dto, encode it and store into Employee instance
        employee.setPassword(passwordEncoder.encode(employeeDto.getPassword()));

        List<EmployeeMetadataElement> metadata = new ArrayList<>(employeeDto.getMetadata().size());
        for (EmployeeMetadataElementDto elementDto : employeeDto.getMetadata()) {
            EmployeeMetadataElement newMetadataElement = new EmployeeMetadataElement();
            elementDto.mapTo(newMetadataElement);
            metadata.add(newMetadataElement);
        }
        employee.setMetadata(metadata);
        employeeDao.create(employee);
    }

    @Transactional
    @Override
    public void delete(Long employeeId) {
        employeeDao.delete(employeeId);
    }

    @Transactional
    @Override
    public void update(EmployeeDto employeeDto) {
        Employee employee = employeeDao.get(employeeDto.getId());

        employeeDto.mapTo(employee);

        // setup employee's metadata, because it was not mapped
        List<EmployeeMetadataElement> metadata = new ArrayList<>(employeeDto.getMetadata().size());

        for (EmployeeMetadataElementDto elementDto : employeeDto.getMetadata()) {
            EmployeeMetadataElement newMetadataElement = new EmployeeMetadataElement();
            elementDto.mapTo(newMetadataElement);
            metadata.add(newMetadataElement);
        }

        employee.setMetadata(metadata);

        employeeDao.update(employee);
    }

    @Transactional(readOnly = true)
    @Override
    public EmployeeDto get(Long id) {
        Employee employee = employeeDao.get(id);

        EmployeeDto result = new EmployeeDto();
        result.mapFrom(employee);
        result.setPostId(employee.getPost().getId());

        ArrayList<EmployeeMetadataElementDto> metadataDto = new ArrayList<>(employee.getMetadata().size());
        for (EmployeeMetadataElement element : employee.getMetadata()) {
            EmployeeMetadataElementDto elementDto = new EmployeeMetadataElementDto();
            elementDto.mapFrom(element);
            metadataDto.add(elementDto);
        }
        result.setMetadata(metadataDto);
        return result;
    }

}
