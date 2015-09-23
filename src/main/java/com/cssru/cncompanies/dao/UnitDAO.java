package com.cssru.cncompanies.dao;

import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.domain.Unit;

import java.util.List;

public interface UnitDAO {
	void addUnit(Unit unit);
	List<Unit> listUnit(Company company);
	List<Unit> listUnitsWithOwner(Human unitOwner);
	List<Unit> listAllUnits(Human manager);
	void removeUnit(Unit unit);
	void updateUnit(Unit unit);
	Unit getUnit(Long id);
}
