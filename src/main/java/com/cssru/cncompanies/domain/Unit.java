package com.cssru.cncompanies.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;


@Entity
@Table (name="units")
public class Unit {

	@Id
	@Column
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn
	private Company company;
	
	@ManyToOne
	@JoinColumn
	private Human manager;
	
	@Column
	private String name;

	@Column
	private String description;
	
	@Column
	private Unit parent;
	
	@OneToMany (mappedBy = "unit")
	private Set<Human> humans;

	//getters
	public Long getId() {
		return id;
	}

	public Company getCompany() {
		return company;
	}

	public Human getManager() {
		return manager;
	}
	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Unit getParent() {
		return parent;
	}

	public Set<Human> getHumans() {
		return humans;
	}
	
	//setters
	public void setId(Long id) {
		this.id = id;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setManager(Human manager) {
		this.manager = manager;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setParent(Unit parent) {
		this.parent = parent;
	}
	
	public void setHumans(Set<Human> humans) {
		this.humans = humans;
	}
	
}
