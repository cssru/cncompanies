package com.cssru.companies.domain;

import com.cssru.companies.enums.StaffType;
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

    @Column(nullable = false)
    @Enumerated(value = EnumType.ORDINAL)
    StaffType type;

    @OneToMany(mappedBy = "staff")
    private List<Post> posts;

}
