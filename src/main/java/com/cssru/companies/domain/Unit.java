package com.cssru.companies.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "units")
public class Unit {

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
    private Company company;

    @Column(nullable = false)
    private Long version;

    @PrePersist
    private void initNewEntity() {
        setVersion(0L);
    }

    @PreUpdate
    private void setNextVersion() {
        setVersion(getVersion() + 1L);
    }

}
