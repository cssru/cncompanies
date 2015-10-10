package com.cssru.cncompanies.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @Column
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String login;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 100)
    private String surname;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String lastname;

    @Column(length = 4096)
    private String note;

    @Column
    private Date birthday;

    @Column(nullable = false)
    private Long version;

    @OneToOne
    @JoinColumn
    private Post post;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<EmployeeMetadataElement> metadata;

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Employee)) return false;
        Employee employee2 = (Employee) o;
        return id.equals(employee2.getId());
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
