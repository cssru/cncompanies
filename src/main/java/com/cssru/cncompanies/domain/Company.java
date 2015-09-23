package com.cssru.cncompanies.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.ANY, setterVisibility = Visibility.NONE)
@Entity
@Table (name="companies")
public class Company implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column (name="id")
	@GeneratedValue
	private Long id;

	@Column (nullable = false)
	private String name;
	
	@ManyToOne
	@JoinColumn (name="owner")
	private Human owner;
	
	@Column
	private String description;

	//getters
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@JsonIgnore
	public Human getOwner() {
		return owner;
	}

	public Long getOwnerId() {
		return owner.getId();
	}
	
	public String getDescription() {
		return description;
	}

	//setters
	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOwner(Human owner) {
		this.owner = owner;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object c) {
		if (c == null || !(c instanceof Company)) return false;
		return this.getId().equals(((Company)c).getId());
	}
}
