package com.cssru.cncompanies.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeMetadataElementDto extends Dto {
    private Long id;
    private Integer type;
    private Long numValue;
    private String strValue;
}
