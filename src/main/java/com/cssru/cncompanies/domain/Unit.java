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


@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.ANY, setterVisibility = Visibility.NONE)
@Entity
@Table (name="units")
public class Unit implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column (name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn (name="company_id")
	private Company company;
	
	@ManyToOne
	@JoinColumn (name="owner_id")
	private Human owner;
	
	@Column (name="name")
	private String name;

	@Column (name="description")
	private String description;
	
	@Column (name="parent_unit_id")
	private Long parentUnitId;
	
	@OneToMany (mappedBy = "unit")
	private Set<Human> humans;

	//getters
	@JsonIgnore
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	@JsonIgnore
	public Company getCompany() {
		return company;
	}

	public Long getCompanyId() {
		return company.getId();
	}

	@JsonIgnore
	public Human getOwner() {
		return owner;
	}
	
	public Long getOwnerId() {
		return owner.getId();
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	@JsonIgnore
	public Long getParentUnitId() {
		return parentUnitId;
	}

	@JsonIgnore
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

	public void setOwner(Human owner) {
		this.owner = owner;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setParentUnitId(Long parentUnitId) {
		this.parentUnitId = parentUnitId;
	}
	
	public void setHumans(Set<Human> humans) {
		this.humans = humans;
	}
	
}
