package com.cssru.cncompanies.service.impl;

import com.cssru.cncompanies.dao.UnitDao;
import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.domain.Login;
import com.cssru.cncompanies.domain.Unit;
import com.cssru.cncompanies.exception.AccessDeniedException;
import com.cssru.cncompanies.service.CompanyService;
import com.cssru.cncompanies.service.HumanService;
import com.cssru.cncompanies.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {

	@Autowired
	private UnitDao unitDao;

	@Autowired
	private HumanService humanService;

	@Autowired
	private CompanyService companyService;

	@Transactional
	@Override
	public void addUnit(Unit unit, Company company, Login managerLogin) throws AccessDeniedException {
		Company existingCompany = companyService.getCompany(company.getId(), managerLogin);
		if (existingCompany == null) {
			throw new AccessDeniedException();
		}
		unit.setCompany(existingCompany);
		unitDao.addUnit(unit);
	}

	@Transactional (readOnly = true)
	@Override
	public List<Unit> listUnit(Company company, Login managerLogin) throws AccessDeniedException {
		Company existingCompany = companyService.getCompany(company.getId(), managerLogin);
		if (existingCompany == null) {
			throw new AccessDeniedException();
		}
		return unitDao.listUnit(existingCompany);
	}

	@Transactional (readOnly = true)
	@Override
	public List<Unit> listUnitsWithOwner(Human unitOwner, Login managerLogin) throws AccessDeniedException {
		List<Unit> units = unitDao.listUnitsWithOwner(unitOwner);
		if (!units.isEmpty()) {
			Unit u = units.get(0);
			if (!(u.getOwner().equals(managerLogin.getHuman()) ||
			u.getCompany().getOwner().equals(managerLogin.getHuman()))) {
				throw new AccessDeniedException();
			}
		}
		return units;
	}

	@Transactional (readOnly = true)
	@Override
	public List<Unit> listAllUnits(Login manager) {
		return unitDao.listAllUnits(manager.getHuman());
	}

	@Transactional
	@Override
	public void removeUnit(Unit unit, Login managerLogin) throws AccessDeniedException {
		Unit existingUnit = unitDao.getUnit(unit.getId());
		if (existingUnit == null || 
				!existingUnit.getCompany().getOwner().equals(managerLogin.getHuman())) {
			throw new AccessDeniedException();
		}

		List<Human> humansOfUnit = humanService.listHuman(unit, managerLogin);
		for (Human h:humansOfUnit)
			humanService.removeHuman(h.getId(), managerLogin);
		unitDao.removeUnit(unit);
	}

	@Transactional
	@Override
	public void updateUnit(Unit unit, Login managerLogin) throws AccessDeniedException {
		Unit existingUnit = unitDao.getUnit(unit.getId());
		if (existingUnit == null || 
				!existingUnit.getCompany().getOwner().equals(managerLogin.getHuman())) {
			throw new AccessDeniedException();
		}
		unitDao.updateUnit(unit);
	}

	@Transactional (readOnly = true)
	@Override
	public Unit getUnit(Long id, Login managerLogin) throws AccessDeniedException {
		Unit existingUnit = unitDao.getUnit(id);
		if (existingUnit == null || 
				!(
						existingUnit.getCompany().getOwner().equals(managerLogin.getHuman()) ||
						existingUnit.getOwner().equals(managerLogin.getHuman())
						)
				) {
			throw new AccessDeniedException();
		}
		return existingUnit;
	}

}
