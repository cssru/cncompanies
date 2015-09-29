package com.cssru.cncompanies.service.impl;

import com.cssru.cncompanies.dao.AccountDao;
import com.cssru.cncompanies.dao.CompanyDao;
import com.cssru.cncompanies.dao.HumanDao;
import com.cssru.cncompanies.dao.UnitDao;
import com.cssru.cncompanies.domain.Account;
import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.domain.Unit;
import com.cssru.cncompanies.dto.CompanyDto;
import com.cssru.cncompanies.exception.AccessDeniedException;
import com.cssru.cncompanies.secure.Role;
import com.cssru.cncompanies.service.CompanyService;
import com.cssru.cncompanies.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        if (companyDto.getManagerId() == null) {
            company.setManager(clientAccount.getHuman()); // default manager is account holder
        } else {
            Human manager = humanService.getHuman(companyDto.getManagerId());

        }

        company.setHolder(clientAccount);
		companyDao.save(company);
	}

	@Transactional (readOnly = true)
	@Override
	public List<CompanyDto> list(Account holder) throws AccessDeniedException {

		if (!Utils.clientHasRole(Role.ACCOUNT_HOLDER)) {
			throw new AccessDeniedException();
		}

        Account clientAccount = Utils.clientAccount(accountDao);
        // holder's account may be deleted by admin during working process
        if (clientAccount == null ||
                // account holder can list only his own companies
                !clientAccount.equals(holder)) {
            throw new AccessDeniedException();
        }

        return companyDao.listByHolder(holder);
	}

    @Transactional (readOnly = true)
    @Override
    public List<Company> list(Human manager) throws AccessDeniedException {

        if (!Utils.clientHasAnyRole(new Role[] {Role.ACCOUNT_HOLDER, Role.COMPANY_MANAGER})) {
            throw new AccessDeniedException();
        }

        Account clientAccount = Utils.clientAccount(accountDao);
        // holder's account may be deleted by admin during working process
        if (clientAccount == null ||
                // account holder can list only his own companies
                !clientAccount.equals(holder)) {
            throw new AccessDeniedException();
        }

        return companyDao.listByHolder(holder);
    }

    @Transactional
	@Override
	public void removeCompany(Long id, Login managerLogin) throws AccessDeniedException {
		Company company = companyDao.getCompany(id);
		
		if (company == null || !company.getOwner().equals(managerLogin.getHuman())) {
			throw new AccessDeniedException();
		}
		
		// delete all units from this company
		List<Unit> unitsOfCompany = unitService.listUnit(company, managerLogin);
		for (Unit u:unitsOfCompany)
			unitService.removeUnit(u, managerLogin);
		companyDao.removeCompany(company);
	}

	@Transactional
	@Override
	public void updateCompany(Company company, Login managerLogin) throws AccessDeniedException {
		Company existingCompany = companyDao.getCompany(company.getId());
		if (existingCompany == null || !existingCompany.getOwner().equals(managerLogin.getHuman())) {
			throw new AccessDeniedException();
		}
		
		companyDao.updateCompany(company);
	}

	@Transactional (readOnly = true)
	@Override
	public Company getCompany(Long id, Login managerLogin) throws AccessDeniedException {
		Company resultCompany = companyDao.getCompany(id);
		if (resultCompany == null || !resultCompany.getOwner().equals(managerLogin.getHuman())) {
			throw new AccessDeniedException();
		}
		
		return resultCompany;
	}

}
