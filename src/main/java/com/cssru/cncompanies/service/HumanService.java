package com.cssru.cncompanies.service;

import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.domain.Login;
import com.cssru.cncompanies.domain.Unit;
import com.cssru.cncompanies.exception.AccessDeniedException;
import com.cssru.cncompanies.synch.SynchContainer;

import java.util.List;

public interface HumanService {
	Human getHuman(Long id, Login managerLogin) throws AccessDeniedException;
	void addHuman(Human human);
	List<Human> listHuman(Unit unit, Login managerLogin) throws AccessDeniedException;
	List<Human> listHuman(Company company, Login managerLogin) throws AccessDeniedException;
	void removeHuman(Long id, Login managerLogin) throws AccessDeniedException;
	void updateHuman(Human human, Login managerLogin) throws AccessDeniedException;
	// RESTful
	SynchContainer<Human> synchronize(SynchContainer<Human> request, Login managerLogin) throws AccessDeniedException;
}
