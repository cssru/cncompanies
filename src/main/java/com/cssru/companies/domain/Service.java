package com.cssru.companies.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "services")
public class Service {
    @Column
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 4096)
    private String description;

    @Column(name = "cost_per_day", nullable = false)
    private Integer costPerDay;

    @PrePersist
    private void initNewEntity() {
        if (costPerDay == null) {
            costPerDay = 0;
        }
    }
}
