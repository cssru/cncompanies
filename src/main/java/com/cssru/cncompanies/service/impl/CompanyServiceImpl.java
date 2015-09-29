package com.cssru.cncompanies.service.impl;

import com.cssru.cncompanies.dao.AccountDao;
import com.cssru.cncompanies.dao.CompanyDao;
import com.cssru.cncompanies.dao.HumanDao;
import com.cssru.cncompanies.dao.UnitDao;
import com.cssru.cncompanies.domain.Account;
import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.dto.CompanyDto;
import com.cssru.cncompanies.exception.AccessDeniedException;
import com.cssru.cncompanies.service.CompanyService;
import com.cssru.cncompanies.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private UnitDao unitDao;

    @Autowired
    private HumanDao humanDao;

    @Autowired
    private AccountDao accountDao;

    @Transactional
    @Override
    public void add(CompanyDto companyDto) throws AccessDeniedException {

        Account clientAccount = Utils.clientAccount(accountDao);

        Company company = new Company();
        companyDto.mapTo(company);

        // company manager not mapped
        if (companyDto.getManagerId() != null) {
            Human companyManager = humanDao.get(companyDto.getManagerId());
            if (companyManager == null) {
                throw new AccessDeniedException();
            }
            company.setManager(companyManager);
        }

        company.setHolder(clientAccount);
        companyDao.save(company);
    }

    @Transactional (readOnly = true)
    @Override
    public List<CompanyDto> list() throws AccessDeniedException {

        Account clientAccount = Utils.clientAccount(accountDao);

        List<Company> companies = companyDao.listByHolder(clientAccount);

        List<CompanyDto> result = new ArrayList<>(companies.size());

        for (Company company : companies) {
            CompanyDto newDto = new CompanyDto();
            newDto.mapFrom(company);
            if (company.getManager() != null) {
                newDto.setManagerId(company.getManager().getId());
            }
            result.add(newDto);
        }

        return result;
    }

    @Transactional (readOnly = true)
    @Override
    public List<CompanyDto> list(Long managerId) throws AccessDeniedException {

        Account clientAccount = Utils.clientAccount(accountDao);

        Human manager = humanDao.get(managerId);

        if (manager == null) {
            throw new AccessDeniedException();
        }

        if (!(manager.getId().equals(managerId) ||
                manager.getUnit().getCompany().getHolder().equals(clientAccount))) {
            throw new AccessDeniedException();
        }

        List<Company> companies = companyDao.listByManager(manager);

        List<CompanyDto> result = new ArrayList<>(companies.size());

        for (Company company : companies) {
            CompanyDto newDto = new CompanyDto();
            newDto.mapFrom(company);
            if (company.getManager() != null) {
                newDto.setManagerId(company.getManager().getId());
            }
            result.add(newDto);
        }

        return result;
    }

    @Transactional
    @Override
    public void delete(Long id) throws AccessDeniedException {
        Company company = companyDao.get(id);

        if (company == null ||
                !company.getHolder().equals(Utils.clientAccount(accountDao))) {
            throw new AccessDeniedException();
        }

        companyDao.delete(company);
    }

    @Transactional
    @Override
    public void update(CompanyDto companyDto) throws AccessDeniedException {
        // get company from database with given index
        Company company = companyDao.get(companyDto.getId());

        // get current user account
        Account clientAccount = Utils.clientAccount(accountDao);

        if (company == null || // if company not exists
                !(company.getManager().equals(clientAccount.getHuman()) || // or current user isn't company manager
                        company.getHolder().equals(clientAccount))) { // or it's holder
            throw new AccessDeniedException();
        }

        boolean managerChanged = company.getManager() != null ? !company.getManager().getId().equals(companyDto.getManagerId()) :
                (companyDto.getManagerId() != null);

        if (managerChanged && // if we need to change company's manager
                company.getHolder().equals(clientAccount)) { // and current user is Account Holder
            if (companyDto.getManagerId() == null) { // if we need to set company's msnsger to null
                company.setManager(null); // we do it
            } else {
                Human newManager = humanDao.get(companyDto.getManagerId()); // if new manager is not null, get it from database by given in DTO id
                if (newManager == null) { // if no such Human exists
                    throw new AccessDeniedException(); // then access must be denied
                }

                if (!(newManager.getUnit().getCompany().getManager().equals(clientAccount.getHuman()) || // if current user isn't new manager's company manager
                        newManager.getUnit().getCompany().getHolder().equals(clientAccount))) { // nor account holder
                    throw new AccessDeniedException(); // then access must be denied
                }
                company.setManager(newManager); // if it's allright, then setup him as manager
            }
        }

        companyDto.mapTo(company); // map other fields to new Company object

        companyDao.update(company);
    }

    @Transactional (readOnly = true)
    @Override
    public Company get(Long id) throws AccessDeniedException {
        Company resultCompany = companyDao.get(id);
        Account clientAccount = Utils.clientAccount(accountDao);

        if (resultCompany == null || // company must exist
                // and current user must be the company manager or Account Holder
                !(resultCompany.getManager().equals(clientAccount.getHuman()) || resultCompany.getHolder().equals(clientAccount))) {
            throw new AccessDeniedException();
        }

        return resultCompany;
    }

}
