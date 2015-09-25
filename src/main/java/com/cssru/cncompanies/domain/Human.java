package com.cssru.cncompanies.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table (name="humans")
public class Human {

	@Id
	@Column
	@GeneratedValue
	private Long id;

	@Column (nullable = false, length = 100)
	private String surname;

	@Column (nullable = false, length = 100)
	private String name;

	@Column (nullable = false, length = 100)
	private String lastname;

	@Column (length = 4096)
	private String note;

	@Column
	private Date birthday;

	@Column (name="last_modified")
	private Date lastModified;

	@OneToMany (mappedBy = "human", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<HumanMetadataElement> metadata;

	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn
	private Unit unit;

	@JsonIgnore
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getLastName() {
		return lastname;
	}

	public String getNote() {
		return note;
	}

	public Date getBirthday() {
		return birthday;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public List<HumanMetadataElement> getMetadata() {
		return metadata;
	}

	public Unit getUnit() {
		return unit;
	}

	//setters
	public void setId(Long id) {
		this.id = id;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLastName(String lastName) {
		this.lastname = lastName;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public void setMetadata(List<HumanMetadataElement> metadata) {
		this.metadata = metadata;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Human)) return false;
		Human human2 = (Human)o;
		return id.equals(human2.getId());
	}
	
}
