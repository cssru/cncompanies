package com.cssru.cncompanies.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@Getter
@Setter
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

}
