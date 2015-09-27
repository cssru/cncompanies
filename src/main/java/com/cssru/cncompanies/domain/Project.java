package com.cssru.cncompanies.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "projects")
public class Project {
    @Column
    @Id
    @GeneratedValue
    private Long id;

    @Column (nullable = false, length = 100)
    private String name;

    @Column (length = 4096)
    private String description;

    @OneToOne
    @JoinColumn (nullable = false)
    private Human manager;

    @OneToMany (mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> tasks;

}
