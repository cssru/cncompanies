package com.cssru.cncompanies.dto;

import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.domain.Unit;

public class UnitDto {
	private Long id;
	private Long ownerId = 0L;
	private String name;
	private String description;
	private Long parentUnitId;
	private Long companyId;

	public UnitDto() {}
	
	public UnitDto(Unit unit) {
		id = unit.getId();
		
		Human owner = unit.getOwner();
		if (owner != null) {
			ownerId = unit.getOwner().getId();
		}

		name = unit.getName();
		description = unit.getDescription();
		parentUnitId = unit.getParentUnitId();
		companyId = unit.getCompany().getId();
	}

	//getters

	public Long getId() {
		return id;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Long getParentUnitId() {
		return parentUnitId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	//setters
	public void setId(Long id) {
		this.id = id;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setParentUnitId(Long parentUnitId) {
		this.parentUnitId = parentUnitId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
}
