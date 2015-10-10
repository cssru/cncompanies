package com.cssru.companies.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceDto extends Dto {
    private Long id;
    private String name;
    private String description;
    private Integer costPerDay;

}
