package com.cssru.cncompanies.domain;

import java.util.Date;
import java.util.Set;

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
	
	@Column (nullable = false, unique = true, length = 50)
	private String login;

	@Column (nullable = false, length = 255)
	private String password;

	@Column (nullable = false, unique = true, length = 50)
	private String email;

	@Column (nullable = false)
	private Boolean locked;

	@Column (nullable = false)
	private Boolean enabled;

	@Column (nullable = false)
	private Date created;

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private Set<ProvidedService> providedServices;

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private Set<Role> roles;

	@Column (nullable = false)
	private Integer money;

	@Column (nullable = false)
	private Long version;

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

	public Boolean getLocked() {
		return locked;
	}

	public Date getCreated() {
		return created;
	}

	public Set<ProvidedService> getProvidedServices() {
		return providedServices;
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

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

    @PrePersist
    private void initMoney() {
        setMoney(0);
        setCreated(new Date());
        setVersion(0L);
        setLocked(false);
        if (enabled == null) {
            setEnabled(false);
        }
    }

    @PreUpdate
    private void setNextVersion() {
        setVersion(getVersion() + 1L);
    }
}
