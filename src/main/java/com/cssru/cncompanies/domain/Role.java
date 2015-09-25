package com.cssru.cncompanies.domain;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Column
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Account account;

    @Column
    @Enumerated (EnumType.ORDINAL)
    private Role role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
