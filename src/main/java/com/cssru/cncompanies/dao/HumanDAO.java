package com.cssru.cncompanies.dao;

import java.util.List;

import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.domain.Login;
import com.cssru.cncompanies.domain.Unit;

public interface HumanDAO {
	Human getHuman(Long id);
	void addHuman(Human human);
	List<Human> listHuman(Unit unit);
	List<Human> listHuman(Company company);
	List<Human> listHuman(long lastModified, Human manager);
	void removeHuman(Human human);
	void removeHumansWithoutLogins();
	void updateHuman(Human human);
}
