package com.cssru.cncompanies.dto;

import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.HumanMetadataElement;
import com.cssru.cncompanies.domain.Unit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class HumanDto {

	private Long id;
	private String surname;
	private String name;
	private String lastname;
	private String note;
	private Date birthday;
	private Long version;
	private Set<HumanMetadataElementDto> metadata;
	private Long unitId;

}
