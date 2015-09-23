package com.cssru.cncompanies.service;

import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.domain.Login;
import com.cssru.cncompanies.domain.Unit;
import com.cssru.cncompanies.exception.AccessDeniedException;

import java.util.List;

public interface UnitService {
	void addUnit(Unit unit, Company company, Login managerLogin) throws AccessDeniedException;
	void updateUnit(Unit unit, Login managerLogin) throws AccessDeniedException;
	List<Unit> listUnit(Company company, Login managerLogin) throws AccessDeniedException;
	List<Unit> listUnitsWithOwner(Human unitOwner, Login managerLogin) throws AccessDeniedException;
	List<Unit> listAllUnits(Login managerLogin);
	void removeUnit(Unit unit, Login managerLogin) throws AccessDeniedException;
	Unit getUnit(Long id, Login managerLogin) throws AccessDeniedException;
}
