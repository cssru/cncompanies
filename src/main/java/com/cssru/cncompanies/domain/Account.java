package com.cssru.cncompanies.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Getter
@Setter
@Entity
@Table (name = "accounts")
public class Account {
	@Id 
	@Column
	@GeneratedValue
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	private Human human;
	
	@Column (nullable = false, unique = true, length = 50)
	private String login;

	@Column (nullable = false, length = 255)
	private String password;

	@Column (nullable = false, unique = true, length = 50)
	private String email;

	@Column (nullable = false)
	private Boolean locked;

	@Column (nullable = false)
	private Boolean enabled;

	@Column (nullable = false)
	private Date created;

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private Set<ProvidedService> providedServices;

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private Set<Role> roles;

	@Column (nullable = false)
	private Integer money;

	@Column (nullable = false)
	private Long version;

    @PrePersist
    private void initNewEntity() {
        setMoney(0);
        setCreated(new Date());
        setVersion(0L);
        setLocked(false);
        if (enabled == null) {
            setEnabled(false);
        }
    }

    @PreUpdate
    private void setNextVersion() {
        setVersion(getVersion() + 1L);
    }

}
