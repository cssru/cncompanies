package com.cssru.cncompanies.domain;

import javax.persistence.*;

@Entity
@Table (name = "services")
public class Service {
    @Column
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Integer costPerDay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCostPerDay() {
        return costPerDay;
    }

    public void setCostPerDay(Integer costPerDay) {
        this.costPerDay = costPerDay;
    }
}
