package com.cssru.cncompanies.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class AccountDto extends Dto {
	private Long id;
	private HumanDto human;
	private String login;
	private String email;
	private Boolean locked;
	private Boolean enabled;
	private Date created;
	private Set<ProvidedServiceDto> providedServices;
	private Integer money;
	private Long version;

}
