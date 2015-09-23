package com.cssru.cncompanies.proxy;

import com.cssru.cncompanies.domain.Login;
import com.cssru.cncompanies.domain.Unit;

public class HumanProxy {
	private Long id;
	private String login;
	private String surname;
	private String name;
	private String lastName;
	private String note;
	private Integer birthday;
	private Long unitId;

	public HumanProxy() {
		this.id = 0L;
		this.surname = "";
		this.name = "";
		this.lastName = "";
		this.note = "";
		this.birthday = 0;
		this.unitId = null;
	}

	public HumanProxy(Login humanLogin) {
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
