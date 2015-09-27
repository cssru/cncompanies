package com.cssru.cncompanies.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name="companies")
public class Company {

	@Id
	@Column
	@GeneratedValue
	private Long id;

	@Column (nullable = false, length = 100)
	private String name;
	
	@ManyToOne
	@JoinColumn (nullable = false)
	private Human owner;
	
	@Column (length = 2048)
	private String description;

	@Column
	private Long version;

	@Override
	public boolean equals(Object c) {
		if (c == null || !(c instanceof Company)) return false;
		return getId().equals(((Company)c).getId());
	}

    @PreUpdate
    private void setNextVersion() {
        setVersion(getVersion() + 1L);
    }

}
