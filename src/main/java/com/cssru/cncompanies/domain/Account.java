package com.cssru.cncompanies.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @Column
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String login;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String surname;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String lastname;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date expires;

    @Column(nullable = false)
    private Boolean enabled;

    @Column(nullable = false)
    private Boolean locked;

    @OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<ProvidedService> providedServices;

    @OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
    private Set<Company> companies;

    @Column(nullable = false)
    private Integer money;

    @Column(nullable = false)
    private Long version;

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Account)) return false;
        Account account2 = (Account) o;
        return id.equals(account2.getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

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
