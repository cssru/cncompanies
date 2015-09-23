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

@Entity
@Table (name="human_metadata")
public class HumanMetadataElement implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column (name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn (name="human_id")
	private Human human;
	
	@Column (name="type")
	private Integer type;
	
	@Column (name="num_value")
	private Long numValue;

	@Column (name="str_value")
	private String strValue;

	@Column (name="image")
	private byte[] image;

	public Long getId() {
		return id;
	}

	public Human getHuman() {
		return human;
	}

	public Integer getType() {
		return type;
	}

	public Long getNumValue() {
		return numValue;
	}

	public String getStrValue() {
		return strValue;
	}

	public byte[] getImage() {
		return image;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setHuman(Human human) {
		this.human = human;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setNumValue(Long numValue) {
		this.numValue = numValue;
	}

	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	
	
}
