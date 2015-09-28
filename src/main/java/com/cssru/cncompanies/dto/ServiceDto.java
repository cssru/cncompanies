package com.cssru.cncompanies.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class ServiceDto {
    private Long id;
    private String name;
    private String description;
    private Integer costPerDay;

}
