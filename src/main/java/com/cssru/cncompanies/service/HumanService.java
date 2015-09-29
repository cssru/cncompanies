package com.cssru.cncompanies.service;

import com.cssru.cncompanies.dto.HumanDto;
import com.cssru.cncompanies.exception.AccessDeniedException;

import java.util.List;

public interface HumanService {
	HumanDto get(Long id) throws AccessDeniedException;
	void add(HumanDto humanDto) throws AccessDeniedException;
	List<HumanDto> listByUnit(Long unitId) throws AccessDeniedException;
	List<HumanDto> listByCompany(Long companyId) throws AccessDeniedException;
	void delete(Long id) throws AccessDeniedException;
	void update(HumanDto humanDto) throws AccessDeniedException;
}
