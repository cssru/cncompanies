package com.cssru.cncompanies.service;

import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.domain.Login;
import com.cssru.cncompanies.domain.Unit;
import com.cssru.cncompanies.dto.HumanDto;
import com.cssru.cncompanies.exception.AccessDeniedException;
import com.cssru.cncompanies.synch.SynchContainer;

import java.util.List;

public interface HumanService {
	HumanDto get(Long id) throws AccessDeniedException;
	void add(HumanDto humanDto);
	List<HumanDto> listByUnit(Long unitId) throws AccessDeniedException;
	List<HumanDto> listByCompany(Long companyId) throws AccessDeniedException;
	void removeHuman(Long id, Login managerLogin) throws AccessDeniedException;
	void updateHuman(Human human, Login managerLogin) throws AccessDeniedException;
	// RESTful
	SynchContainer<Human> synchronize(SynchContainer<Human> request, Login managerLogin) throws AccessDeniedException;
}
