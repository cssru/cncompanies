package com.cssru.companies.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "companies")
public class Company {

    @Id
    @Column
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 4096)
    private String description;

    @OneToOne
    @JoinColumn(nullable = false)
    private Staff staff;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Account account;

    @Column(nullable = false)
    private Long version;

    @OneToMany(mappedBy = "company")
    private List<Unit> units;

    @Override
    public boolean equals(Object c) {
        if (c == null || !(c instanceof Company)) return false;
        return getId().equals(((Company) c).getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
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
