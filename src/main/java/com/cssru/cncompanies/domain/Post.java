package com.cssru.cncompanies.domain;

import com.cssru.cncompanies.secure.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @Column
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 4096)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Role role;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Staff staff;

    @OneToMany(mappedBy = "executor")
    private List<Task> tasks;

    @OneToOne(mappedBy = "post")
    private Employee employee;

}
