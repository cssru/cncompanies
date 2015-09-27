package com.cssru.cncompanies.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table (name = "provided_services")
public class ProvidedService {
    @Column
    @Id
    @GeneratedValue
    private Long id;

    @Column (nullable = false)
    private Date begin;

    @Column (nullable = false)
    private Date end;

    @Column (name = "paid_money", nullable = false)
    private Integer paidMoney;

    @ManyToOne
    @JoinColumn (nullable = false)
    private Service service;

    @Column (nullable = false)
    private Account account;

}
