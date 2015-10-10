package com.cssru.cncompanies.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "staffs")
public class Staff {
    @Id
    @Column
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @OneToMany(mappedBy = "staff")
    private List<Post> posts;

}
