package com.cssru.cncompanies.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
	private Human manager;

	@ManyToOne
	@JoinColumn (nullable = false)
	private Account holder;

	@Column (length = 2048)
	private String description;

	@Column (nullable = false)
	private Long version;

	@Override
	public boolean equals(Object c) {
		if (c == null || !(c instanceof Company)) return false;
		return getId().equals(((Company)c).getId());
	}

	@PrePersist
	private void initNewEntity() {
        setVersion(0L);
    }

    @PreUpdate
    private void setNextVersion() {
        setVersion(getVersion() + 1L);
    }

}
