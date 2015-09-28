package com.cssru.cncompanies.dto;

import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.secure.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class AccountDto {
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
