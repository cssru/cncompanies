package com.cssru.companies.dto;

import com.cssru.companies.annotation.NotMapped;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDto extends Dto {
    private Long id;
    private String name;

    @NotMapped
    private Long managerId;

    private String description;
    private Long version;
}
