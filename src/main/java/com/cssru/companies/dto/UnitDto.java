package com.cssru.companies.dto;

import com.cssru.companies.annotation.NotMapped;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnitDto extends Dto {
    private Long id;

    @NotMapped
    private Long managerId;

    @NotMapped
    private String managerName;

    private String name;
    private String description;

    @NotMapped
    private Long companyId;
}
