package com.cssru.cncompanies.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
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

	@Column (nullable = false)
	private Long version;

	@OneToMany (mappedBy = "human", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<HumanMetadataElement> metadata;

	@ManyToOne
	@JoinColumn (nullable = false)
	private Unit unit;

	@OneToMany (mappedBy = "manager")
	private List<Company> managedCompanies;

	@OneToMany (mappedBy = "manager")
	private List<Unit> managedUnits;

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Human)) return false;
		Human human2 = (Human)o;
		return id.equals(human2.getId());
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
