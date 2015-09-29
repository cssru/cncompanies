package com.cssru.cncompanies.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


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
	@JoinColumn (nullable = false)
	private Company company;
	
	@ManyToOne
	@JoinColumn
	private Human manager;
	
	@Column (nullable = false, length = 100)
	private String name;

	@Column (length = 4096)
	private String description;
	
	@Column
	private Unit parent;

    @Column (nullable = false)
    private Long version;

	@OneToMany (mappedBy = "unit")
	private List<Human> humans;

    @PrePersist
    private void initNewEntity() {
        setVersion(0L);
    }

    @PreUpdate
    private void setNextVersion() {
        setVersion(getVersion() + 1L);
    }

}
