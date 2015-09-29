package com.cssru.cncompanies.dao;

import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.domain.Unit;

import java.util.List;

public interface UnitDao {
	void add(Unit unit);
	List<Unit> list(Company company);
	List<Unit> list(Human unitManager);
	List<Unit> listVisible(Human manager);
	void delete(Unit unit);
	void update(Unit unit);
	Unit get(Long id);
}
