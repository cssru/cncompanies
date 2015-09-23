package com.cssru.cncompanies.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cssru.cncompanies.dao.CompanyDAO;
import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Login;
import com.cssru.cncompanies.domain.Unit;
import com.cssru.cncompanies.exception.AccessDeniedException;
import com.cssru.cncompanies.service.CompanyService;
import com.cssru.cncompanies.service.UnitService;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyDAO companyDAO;

	@Autowired
	private UnitService unitService;

	@Transactional
	@Override
	public void addCompany(Company company, Login managerLogin) {
		company.setOwner(managerLogin.getHuman());
		companyDAO.addCompany(company);
	}

	@Transactional (readOnly = true)
	@Override
	public List<Company> listCompany(Login managerLogin) {
		return companyDAO.listCompany(managerLogin.getHuman());
	}

	@Transactional
	@Override
	public void removeCompany(Long id, Login managerLogin) throws AccessDeniedException {
		Company company = companyDAO.getCompany(id);
		
		if (company == null || !company.getOwner().equals(managerLogin.getHuman())) {
			throw new AccessDeniedException();
		}
		
		// delete all units from this company
		List<Unit> unitsOfCompany = unitService.listUnit(company, managerLogin);
		for (Unit u:unitsOfCompany)
			unitService.removeUnit(u, managerLogin);
		companyDAO.removeCompany(company);
	}

	@Transactional
	@Override
	public void updateCompany(Company company, Login managerLogin) throws AccessDeniedException {
		Company existingCompany = companyDAO.getCompany(company.getId());
		if (existingCompany == null || !existingCompany.getOwner().equals(managerLogin.getHuman())) {
			throw new AccessDeniedException();
		}
		
		companyDAO.updateCompany(company);
	}

	@Transactional (readOnly = true)
	@Override
	public Company getCompany(Long id, Login managerLogin) throws AccessDeniedException {
		Company resultCompany = companyDAO.getCompany(id);
		if (resultCompany == null || !resultCompany.getOwner().equals(managerLogin.getHuman())) {
			throw new AccessDeniedException();
		}
		
		return resultCompany;
	}

}
