package com.cssru.cncompanies.dto;

import com.cssru.cncompanies.domain.Account;
import com.cssru.cncompanies.domain.Service;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
public class ProvidedServiceDto {
    private Long id;
    private Date begin;
    private Date end;
    private Integer paidMoney;
    private ServiceDto service;

}
