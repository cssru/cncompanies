package com.cssru.cncompanies.dto;

import com.cssru.cncompanies.annotation.Email;
import com.cssru.cncompanies.domain.Login;
import com.cssru.cncompanies.domain.Unit;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class AccountDto {

	private Long id;

	@NotNull
	@NotEmpty
	private String login;

	@NotNull
	@NotEmpty
	private String password;

	@NotNull
	@NotEmpty
	private String passwordConfirm;

	@NotNull
	@NotEmpty
	@Email
	private String email;

	@NotNull
	@NotEmpty
	private String surname;

	@NotNull
	@NotEmpty
	private String name;

	@NotNull
	@NotEmpty
	private String lastName;

	private String note;
	private Integer birthday;
	private Long unitId;

	public AccountDto() {
	}

	public AccountDto(Login humanLogin) {
		this.id = humanLogin.getHuman().getId();
		this.login = humanLogin.getLogin();
		this.surname = humanLogin.getHuman().getSurname();
		this.name = humanLogin.getHuman().getName();
		this.lastName = humanLogin.getHuman().getLastName();
		this.note = humanLogin.getHuman().getNote();
		this.birthday = humanLogin.getHuman().getBirthday();
		Unit unit = humanLogin.getHuman().getUnit();
		if (unit == null) {
			this.unitId = 0L;
		} else {
			this.unitId = unit.getId();
		}
	}

	public Long getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public String getEmail() {
		return email;
	}

	public String getSurname() {
		return surname;
	}

	public String getName() {
		return name;
	}

	public String getLastName() {
		return lastName;
	}

	public String getNote() {
		return note;
	}

	public Integer getBirthday() {
		return birthday;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setBirthday(Integer birthday) {
		this.birthday = birthday;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

}
