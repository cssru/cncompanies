package com.cssru.cncompanies.service.impl;

import com.cssru.cncompanies.dao.AccountDao;
import com.cssru.cncompanies.dao.CompanyDao;
import com.cssru.cncompanies.dao.HumanDao;
import com.cssru.cncompanies.dao.UnitDao;
import com.cssru.cncompanies.domain.*;
import com.cssru.cncompanies.dto.HumanDto;
import com.cssru.cncompanies.dto.HumanMetadataElementDto;
import com.cssru.cncompanies.exception.AccessDeniedException;
import com.cssru.cncompanies.service.HumanService;
import com.cssru.cncompanies.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class HumanServiceImpl implements HumanService {

    @Autowired
    private HumanDao humanDao;

    @Autowired
    private UnitDao unitDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private AccountDao accountDao;

    @Transactional
    @Override
    public void add(HumanDto humanDto) throws AccessDeniedException {

        Unit unit = unitDao.get(humanDto.getUnitId());

        if (unit == null) {
            throw new AccessDeniedException();
        }

        Account clientAccount = Utils.clientAccount(accountDao);
        // to add employee client must be the Unit Manager, the Company Manager or the Account Holder
        if (!((unit.getManager() != null && unit.getManager().equals(clientAccount.getEmployee())) ||
                (unit.getCompany().getManager() != null && unit.getCompany().getManager().equals(clientAccount.getEmployee())) ||
                unit.getCompany().getHolder().equals(clientAccount)
        )) {
            throw new AccessDeniedException();
        }

        Employee employee = new Employee();
        humanDto.mapTo(employee);
        // employee's unit and metadata was not mapped
        employee.setUnit(unit);
        // setup employee's metadata
        List<EmployeeMetadataElement> metadata = new ArrayList<>(humanDto.getMetadata().size());
        for (HumanMetadataElementDto elementDto : humanDto.getMetadata()) {
            EmployeeMetadataElement newMetadataElement = new EmployeeMetadataElement();
            elementDto.mapTo(newMetadataElement);
            metadata.add(newMetadataElement);
        }
        employee.setMetadata(metadata);
        humanDao.add(employee);
    }

    @Transactional(readOnly = true)
    @Override
    public List<HumanDto> listByUnit(Long unitId) throws AccessDeniedException {

        Unit unit = unitDao.get(unitId);

        if (unit == null) {
            throw new AccessDeniedException();
        }

        Account clientAccount = Utils.clientAccount(accountDao);
        // to list unit's employees client must be the Unit Manager, the Company Manager or the Account Holder
        if (!((unit.getManager() != null && unit.getManager().equals(clientAccount.getEmployee())) ||
                (unit.getCompany().getManager() != null && unit.getCompany().getManager().equals(clientAccount.getEmployee())) ||
                unit.getCompany().getHolder().equals(clientAccount)
        )) {
            throw new AccessDeniedException();
        }

        List<HumanDto> result = new ArrayList<>(unit.getEmployees().size());
        for (Employee employee : unit.getEmployees()) {
            HumanDto humanDto = new HumanDto();
            humanDto.mapFrom(employee);
            humanDto.setUnitId(unitId);
            ArrayList<HumanMetadataElementDto> metadataDto = new ArrayList<>(employee.getMetadata().size());
            for (EmployeeMetadataElement element : employee.getMetadata()) {
                HumanMetadataElementDto elementDto = new HumanMetadataElementDto();
                elementDto.mapFrom(element);
                metadataDto.add(elementDto);
            }
            humanDto.setMetadata(metadataDto);
        }

        return result;

    }

    @Transactional(readOnly = true)
    @Override
    public List<HumanDto> listByCompany(Long companyId) throws AccessDeniedException {
        Company company = companyDao.get(companyId);

        if (company == null) {
            throw new AccessDeniedException();
        }

        Account clientAccount = Utils.clientAccount(accountDao);
        // to list unit's employees client must be the Company Manager or the Account Holder
        if (!((company.getManager() != null && company.getManager().equals(clientAccount.getEmployee())) ||
                company.getHolder().equals(clientAccount)
        )) {
            throw new AccessDeniedException();
        }

        List<Employee> allEmployees = humanDao.list(company);

        List<HumanDto> result = new ArrayList<>(allEmployees.size());
        for (Employee employee : allEmployees) {
            HumanDto humanDto = new HumanDto();
            humanDto.mapFrom(employee);
            humanDto.setUnitId(employee.getUnit().getId());
            ArrayList<HumanMetadataElementDto> metadataDto = new ArrayList<>(employee.getMetadata().size());
            for (EmployeeMetadataElement element : employee.getMetadata()) {
                HumanMetadataElementDto elementDto = new HumanMetadataElementDto();
                elementDto.mapFrom(element);
                metadataDto.add(elementDto);
            }
            humanDto.setMetadata(metadataDto);
        }

        return result;

    }

    @Transactional
    @Override
    public void delete(Long id) throws AccessDeniedException {
        Employee employee = humanDao.get(id);

        Account clientAccount = Utils.clientAccount(accountDao);
        // to list unit's employees client must be the Unit Manager, the Company Manager or the Account Holder
        if (!((employee.getUnit().getManager() != null && employee.getUnit().getManager().equals(clientAccount.getEmployee())) ||
                (employee.getUnit().getCompany().getManager() != null && employee.getUnit().getCompany().getManager().equals(clientAccount.getEmployee())) ||
                employee.getUnit().getCompany().getHolder().equals(clientAccount)
        )) {
            throw new AccessDeniedException();
        }

        humanDao.delete(employee);
    }

    @Transactional
    @Override
    public void update(HumanDto humanDto) throws AccessDeniedException {
        Employee employee = humanDao.get(humanDto.getId());

        if (employee == null) {
            throw new AccessDeniedException();
        }

        if (!employee.getUnit().getId().equals(humanDto.getUnitId())) {
            // it happens when someone try to change Employee's unit (move employee to another unit)
            // this person must be Company Manager or Account Holder
            Unit unit = unitDao.get(humanDto.getUnitId());

            if (unit == null) {
                throw new AccessDeniedException();
            }

            Account clientAccount = Utils.clientAccount(accountDao);
            // to change employee's unit client must be the Company Manager or the Account Holder
            if (!((employee.getUnit().getCompany().getManager() != null && employee.getUnit().getCompany().getManager().equals(clientAccount.getEmployee())) ||
                    employee.getUnit().getCompany().getHolder().equals(clientAccount) ||
                    (unit.getCompany().getManager() != null && unit.getCompany().getManager().equals(clientAccount.getEmployee())) ||
                    unit.getCompany().getHolder().equals(clientAccount)
            )) {
                throw new AccessDeniedException();
            }
            employee.setUnit(unit);
        }

        humanDto.mapTo(employee);

        // setup employee's metadata, because it was not mapped
        List<EmployeeMetadataElement> metadata = new ArrayList<>(humanDto.getMetadata().size());

        for (HumanMetadataElementDto elementDto : humanDto.getMetadata()) {
            EmployeeMetadataElement newMetadataElement = new EmployeeMetadataElement();
            elementDto.mapTo(newMetadataElement);
            metadata.add(newMetadataElement);
        }

        employee.setMetadata(metadata);

        humanDao.update(employee);
    }

    @Transactional(readOnly = true)
    @Override
    public HumanDto get(Long id) throws AccessDeniedException {
        Employee employee = humanDao.get(id);

        if (employee == null) {
            throw new AccessDeniedException();
        }

        Account clientAccount = Utils.clientAccount(accountDao);
        // to get employee client must be the Unit Manager, the Company Manager or the Account Holder
        if (!((employee.getUnit().getManager() != null && employee.getUnit().getManager().equals(clientAccount.getEmployee())) ||
                (employee.getUnit().getCompany().getManager() != null && employee.getUnit().getCompany().getManager().equals(clientAccount.getEmployee())) ||
                employee.getUnit().getCompany().getHolder().equals(clientAccount)
        )) {
            throw new AccessDeniedException();
        }

        HumanDto result = new HumanDto();
        result.mapFrom(employee);
        result.setUnitId(employee.getUnit().getId());

        ArrayList<HumanMetadataElementDto> metadataDto = new ArrayList<>(employee.getMetadata().size());
        for (EmployeeMetadataElement element : employee.getMetadata()) {
            HumanMetadataElementDto elementDto = new HumanMetadataElementDto();
            elementDto.mapFrom(element);
            metadataDto.add(elementDto);
        }
        result.setMetadata(metadataDto);
        return result;
    }

}
