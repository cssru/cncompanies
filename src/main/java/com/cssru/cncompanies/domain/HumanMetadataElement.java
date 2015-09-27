package com.cssru.cncompanies.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

	@Column (name = "raw_data")
	private byte[] rawData;

}
