package com.cssru.cncompanies.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class ServiceDto extends Dto {
    private Long id;
    private String name;
    private String description;
    private Integer costPerDay;

}
