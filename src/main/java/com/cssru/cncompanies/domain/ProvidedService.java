package com.cssru.cncompanies.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table (name = "provided_services")
public class ProvidedService {
    @Column
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Date begin;

    @Column
    private Date end;

    @Column (name = "paid_money")
    private Integer paidMoney;

    @ManyToOne
    @JoinColumn (name = "id")
    private Service service;

    @Column
    private Account account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Integer getPaidMoney() {
        return paidMoney;
    }

    public void setPaidMoney(Integer paidMoney) {
        this.paidMoney = paidMoney;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
