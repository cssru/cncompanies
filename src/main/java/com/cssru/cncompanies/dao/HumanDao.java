package com.cssru.cncompanies.dao;

import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.domain.Unit;

import java.util.List;

public interface HumanDao {
	Human get(Long id);
	void add(Human human);
	List<Human> list(Unit unit);
	List<Human> list(Company company);
	List<Human> list(long lastModified, Human manager);
	void delete(Long id);
    void delete(Human human);
	void deleteWithoutLogins();
	void update(Human human);
}
