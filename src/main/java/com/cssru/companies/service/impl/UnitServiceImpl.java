package com.cssru.companies.service.impl;

import com.cssru.companies.dao.AccountDao;
import com.cssru.companies.dao.CompanyDao;
import com.cssru.companies.dao.EmployeeDao;
import com.cssru.companies.dao.UnitDao;
import com.cssru.companies.domain.Account;
import com.cssru.companies.domain.Company;
import com.cssru.companies.domain.Employee;
import com.cssru.companies.domain.Unit;
import com.cssru.companies.dto.UnitDto;
import com.cssru.companies.service.UnitService;
import com.cssru.companies.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    private UnitDao unitDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private AccountDao accountDao;

    @Transactional
    @Override
    public void add(UnitDto unitDto) throws AccessDeniedException {
        Account clientAccount = Utils.clientAccount(accountDao);
        Company company = companyDao.get(unitDto.getCompanyId());
        if (company == null) {
            throw new AccessDeniedException();
        }

        if (!company.getHolder().equals(clientAccount))
            if (company.getManager() != null) {
                if (!company.getManager().equals(clientAccount.getEmployee())) {
                    throw new AccessDeniedException(); // if company has manager and current user isn't company's Account Holder nor company's manager
                }
            } else {
                throw new AccessDeniedException(); // if company has no manager and current user isn't company's Account Holder
            }
        // now current user has rights to add new Unit to company

        // check if unit manager is accessible for current user
        Employee manager = employeeDao.get(unitDto.getManagerId());

        if (manager == null) {
            throw new AccessDeniedException();
        }

        if (!manager.getUnit().getCompany().getHolder().equals(clientAccount))
            if (manager.getUnit().getCompany().getManager() != null) {
                if (!manager.getUnit().getCompany().getManager().equals(clientAccount.getEmployee())) {
                    throw new AccessDeniedException(); // if current user isn't pretendent's company's Account Holder nor pretendent's company's manager
                }
            } else {
                throw new AccessDeniedException(); // if pretendent's company has no manager and current user isn't company's Account Holder
            }
        Unit unit = new Unit();
        unitDto.mapTo(unit);
        unit.setCompany(company);
        unit.setManager(manager);

        unitDao.add(unit);
    }

    @Transactional(readOnly = true)
    @Override
    public UnitDto get(Long id) throws AccessDeniedException {
        Account clientAccount = Utils.clientAccount(accountDao);
        Unit unit = unitDao.get(id);

        if (unit == null) {
            throw new AccessDeniedException();
        }

        if (!(unit.getCompany().getHolder().equals(clientAccount) ||
                (unit.getCompany().getManager() != null && unit.getCompany().getManager().equals(clientAccount.getEmployee())) ||
                (unit.getManager() != null && unit.getManager().equals(clientAccount.getEmployee())))) {
            throw new AccessDeniedException();
        }
        UnitDto result = new UnitDto();
        result.mapFrom(unit);
        result.setManagerId(unit.getManager() != null ? unit.getManager().getId() : null);
        result.setCompanyId(unit.getCompany().getId());
        result.setManagerName(unit.getManager() != null ? unit.getManager().getName() : "");

        return result;
    }

    @Transactional
    @Override
    public void update(UnitDto unitDto) throws AccessDeniedException {
        Account clientAccount = Utils.clientAccount(accountDao);

        Unit unit = unitDao.get(unitDto.getId());
        if (unit == null) {
            throw new AccessDeniedException();
        }

        if (!(
                (unit.getCompany().getManager() != null && unit.getCompany().getManager().equals(clientAccount.getEmployee())) ||
                        unit.getCompany().getHolder().equals(clientAccount)
        )) {
            throw new AccessDeniedException();
        }

        Company company = companyDao.get(unitDto.getCompanyId());
        if (company == null) {
            throw new AccessDeniedException();
        }

        if (!company.getHolder().equals(clientAccount))
            if (company.getManager() != null) {
                if (!company.getManager().equals(clientAccount.getEmployee())) {
                    throw new AccessDeniedException(); // if company has manager and current user isn't company's Account Holder nor company's manager
                }
            } else {
                throw new AccessDeniedException(); // if company has no manager and current user isn't company's Account Holder
            }
        // now current user has rights to add new Unit to company

        // check if unit manager is accessible for current user
        Employee manager = employeeDao.get(unitDto.getManagerId());

        if (manager == null) {
            throw new AccessDeniedException();
        }

        if (!manager.getUnit().getCompany().getHolder().equals(clientAccount))
            if (manager.getUnit().getCompany().getManager() != null) {
                if (!manager.getUnit().getCompany().getManager().equals(clientAccount.getEmployee())) {
                    throw new AccessDeniedException(); // if current user isn't pretendent's company's Account Holder nor pretendent's company's manager
                }
            } else {
                throw new AccessDeniedException(); // if pretendent's company has no manager and current user isn't company's Account Holder
            }


        unitDto.mapTo(unit);
        unit.setCompany(company);
        unit.setManager(manager);

        unitDao.update(unit);
    }

    @Transactional
    @Override
    public void delete(Long id) throws AccessDeniedException {
        Account clientAccount = Utils.clientAccount(accountDao);

        Unit unit = unitDao.get(id);

        if (unit == null) {
            throw new AccessDeniedException();
        }

        if (!(
                unit.getCompany().getHolder().equals(clientAccount) ||
                        (unit.getCompany().getManager() != null && unit.getCompany().getManager().equals(clientAccount.getEmployee()))
        )) {
            throw new AccessDeniedException();
        }

        unitDao.delete(unit);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UnitDto> listByCompany(Long companyId) throws AccessDeniedException {
        Account clientAccount = Utils.clientAccount(accountDao);

        Company company = companyDao.get(companyId);

        if (company == null) {
            throw new AccessDeniedException();
        }

        if (!(
                (company.getManager() != null && company.getManager().equals(clientAccount.getEmployee())) ||
                        company.getHolder().equals(clientAccount)
        )) {
            throw new AccessDeniedException();
        }

        List<UnitDto> result = new ArrayList<>(company.getUnits().size());
        for (Unit unit : company.getUnits()) {
            UnitDto newDto = new UnitDto();
            newDto.mapFrom(unit);
            newDto.setCompanyId(company.getId());
            newDto.setManagerId(unit.getManager() != null ? unit.getManager().getId() : null);
            newDto.setManagerName(Utils.humanName(unit.getManager()));
            result.add(newDto);
        }

        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public List<UnitDto> listByManager(Long managerId) throws AccessDeniedException {
        Account clientAccount = Utils.clientAccount(accountDao);

        Employee employee = employeeDao.get(managerId);

        if (company == null) {
            throw new AccessDeniedException();
        }

        if (!(
                (company.getManager() != null && company.getManager().equals(clientAccount.getEmployee())) ||
                        company.getHolder().equals(clientAccount)
        )) {
            throw new AccessDeniedException();
        }

        List<UnitDto> result = new ArrayList<>(company.getUnits().size());
        for (Unit unit : company.getUnits()) {
            UnitDto newDto = new UnitDto();
            newDto.mapFrom(unit);
            newDto.setCompanyId(company.getId());
            newDto.setManagerId(unit.getManager() != null ? unit.getManager().getId() : null);
            newDto.setManagerName(Utils.humanName(unit.getManager()));
            result.add(newDto);
        }

        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public List<UnitDto> list() {
        Account clientAccount = Utils.clientAccount(accountDao);

    }

}
