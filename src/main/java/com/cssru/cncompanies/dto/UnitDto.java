package com.cssru.cncompanies.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnitDto extends Dto {

    private Long id;
	private Long managerId;
	private String name;
	private String description;
	private Long parentUnitId;
	private Long companyId;

}
