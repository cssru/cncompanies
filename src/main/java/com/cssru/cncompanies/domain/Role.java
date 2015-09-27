package com.cssru.cncompanies.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {
    @Column
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn (nullable = false)
    private Account account;

    @Column (nullable = false)
    @Enumerated (EnumType.ORDINAL)
    private Role role;

}
