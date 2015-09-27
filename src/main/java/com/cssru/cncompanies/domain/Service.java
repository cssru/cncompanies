package com.cssru.cncompanies.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table (name = "services")
public class Service {
    @Column
    @Id
    @GeneratedValue
    private Long id;

    @Column (nullable = false, length = 100)
    private String name;

    @Column (nullable = true, length = 4096)
    private String description;

    @Column (nullable = false)
    private Integer costPerDay;

    @PrePersist
    private void initNewEntity() {
        if (costPerDay == null) {
            costPerDay = 0;
        }
    }
}
