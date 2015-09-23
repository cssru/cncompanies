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
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.ANY, setterVisibility = Visibility.NONE)
public class Human implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id 
	@Column (name="id")
	@GeneratedValue
	private Long id;

	@Transient
	private Long deviceClientId;

	@Column (nullable = false, length = 100)
	private String surname;

	@Column (nullable = false, length = 100)
	private String name;

	@Column (nullable = false, length = 100)
	private String lastname;

	@Column (length = 4096)
	private String note;

	@Column
	private Integer birthday;

	@Column (name="last_modified")
	private Date lastModified;

	@OneToMany (mappedBy = "human", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<HumanMetadataElement> metadata;

	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn (name="unit_id")
	private Unit unit;

	@JsonIgnore
	public Long getId() {
		return id;
	}

	public Long getServerId() {
		return id;
	}

	public Long getClientId() {
		return deviceClientId;
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

	public Integer getBirthday() {
		return birthday;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public List<HumanMetadataElement> getMetadata() {
		return metadata;
	}

	@JsonIgnore
	public Unit getUnit() {
		return unit;
	}

	public String getUnitName() {
		return unit != null ? unit.getName() : null;
	}

	public Long getUnitId() {
		return unit != null ? unit.getId() : 0L;
	}

	@JsonIgnore
	public String getFullName() {
		return new StringBuilder()
				.append(surname)
				.append(" ")
				.append(name)
				.append(" ")
				.append(lastname).toString();
	}

	@JsonIgnore
	public String getShortName() {
		return surname + " " +
				(name != null ? (name.substring(0,1)+"."):"") + 
						(lastname != null ? (lastname.substring(0, 1)+"."):"");
	}

	//setters
	public void setId(Long id) {
		this.id = id;
	}

	public void setServerId(Long id) {
		this.id = id;
	}

	public void setClientId(Long clientId) {
		this.deviceClientId = id;
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

	public void setBirthday(Integer birthday) {
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
	
	@Override
	public String toString() {
		return getShortName();
	}

}
