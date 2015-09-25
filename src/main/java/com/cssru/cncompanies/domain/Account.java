package com.cssru.cncompanies.domain;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.Type;

@Entity
@Table (name = "accounts")
public class Account {
	@Id 
	@Column
	@GeneratedValue
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	private Human human;
	
	@Column (unique = true, length = 30)
	private String login;

	@Column (length = 255)
	private String password;

	@Column (length = 50)
	private String email;

	@Column (nullable = false)
	private Boolean locked;

	@Column (nullable = false)
	private Boolean enabled;

	@Column
	private Date created;

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private Set<ProvidedService> providedServices;

	@Column
	private Long version;

	public Account() {
		locked = false;
	}
	
	public Account(Human human) {
		this();
		this.human = human;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Long getId() {
		return id;
	}

	public Human getHuman() {
		return human;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getConfirmCode() {
		return confirmCode;
	}

	public boolean isConfirmed() {
		return confirmCode == null || confirmCode.length() == 0; 
	}
	
	public Boolean getLocked() {
		return locked;
	}

	public Date getCreated() {
		return created;
	}

	public Set<ProvidedService> getProvidedServices() {
		return providedServices;
	}

	public Date getPaidTill() {
		return paidTill;
	}

	public Long getVersion() {
		return version;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setHuman(Human human) {
		this.human = human;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setProvidedServices(Set<ProvidedService> providedServices) {
		this.providedServices = providedServices;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}
