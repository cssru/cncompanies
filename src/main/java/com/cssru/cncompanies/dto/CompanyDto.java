package com.cssru.cncompanies.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDto extends Dto {

	private Long id;
	private String name;
	private Long managerId;
	private String description;
	private Long version;

}
