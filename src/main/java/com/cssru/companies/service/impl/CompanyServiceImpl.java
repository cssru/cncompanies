package com.cssru.companies.service.impl;

import com.cssru.companies.dao.AccountDao;
import com.cssru.companies.dao.CompanyDao;
import com.cssru.companies.dao.StaffDao;
import com.cssru.companies.domain.Account;
import com.cssru.companies.domain.Company;
import com.cssru.companies.domain.Staff;
import com.cssru.companies.dto.CompanyDto;
import com.cssru.companies.service.CompanyService;
import com.cssru.companies.utils.Utils;
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
    private AccountDao accountDao;

    @Autowired
    private StaffDao staffDao;

    @Transactional
    @Override
    public void create(CompanyDto companyDto) {
        Company company = new Company();

        companyDto.mapTo(company);
        company.setAccount(Utils.clientAccount(accountDao));
        company.setStaff(staffDao.get(companyDto.getStaffId()));
        companyDao.create(company);
    }

    @Transactional(readOnly = true)
    @Override
    public CompanyDto get(Long id) {
        Company company = companyDao.get(id);
        CompanyDto companyDto = new CompanyDto();

        if (company != null) {
            companyDto.mapFrom(company);
            companyDto.setAccountId(company.getAccount().getId());
            Staff staff = company.getStaff();
            companyDto.setStaffId(staff != null ? staff.getId() : null);
        }

        return companyDto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CompanyDto> listByHolder(Long holderAccountId) {
        Account holderAccount = accountDao.get(holderAccountId);
        List<Company> companyList = companyDao.list(holderAccount);
        List<CompanyDto> companyDtoList = new ArrayList<>(companyList.size());

        for (Company company : companyList) {
            CompanyDto nextCompanyDto = new CompanyDto();
            nextCompanyDto.mapFrom(company);
            nextCompanyDto.setAccountId(company.getAccount().getId());
            Staff staff = company.getStaff();
            nextCompanyDto.setStaffId(staff != null ? staff.getId() : null);
            companyDtoList.add(nextCompanyDto);
        }

        return companyDtoList;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        companyDao.delete(id);
    }

    @Transactional
    @Override
    public void update(CompanyDto companyDto) {
        Company company = companyDao.get(companyDto.getId());
        companyDto.mapTo(company);

        Account account = accountDao.get(companyDto.getAccountId());
        if (account != null) {
            company.setAccount(account);
        }

        company.setStaff(
                companyDto.getStaffId() != null
                        ?
                        staffDao.get(companyDto.getStaffId())
                        :
                        null
        );

        companyDao.update(company);
    }

}
