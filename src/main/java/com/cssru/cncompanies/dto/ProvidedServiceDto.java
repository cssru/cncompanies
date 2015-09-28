package com.cssru.cncompanies.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProvidedServiceDto extends Dto {
    private Long id;
    private Date begin;
    private Date end;
    private Integer paidMoney;
    private ServiceDto service;

}
