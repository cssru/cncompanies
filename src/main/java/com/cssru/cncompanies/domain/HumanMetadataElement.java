package com.cssru.cncompanies.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table (name="human_metadata")
public class HumanMetadataElement {
	
	@Id
	@Column
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn (nullable = false)
	private Human human;
	
	@Column (nullable = false)
	private Integer type;
	
	@Column (name = "num_value")
	private Long numValue;

	@Column (name = "str_value")
	private String strValue;

}
