package com.cssru.cncompanies.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "human_metadata")
public class EmployeeMetadataElement {

    @Id
    @Column
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private Integer type;

    @Column(name = "num_value1")
    private Long numValue1;

    @Column(name = "num_value2")
    private Long numValue2;

    @Column(name = "str_value1")
    private String strValue1;

    @Column(name = "str_value2")
    private String strValue2;

}
