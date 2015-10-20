package com.cssru.companies.dto;

import com.cssru.companies.annotation.NotMapped;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDto extends Dto {
    private Long id;
    private String name;
    private String description;

    @NotMapped
    private Long accountId;
    @NotMapped
    private Long staffId;
}
