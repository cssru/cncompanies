package com.cssru.cncompanies.dto;

import com.cssru.cncompanies.annotation.NotMapped;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class EmployeeDto extends Dto {

    private Long id;
    private String login;

    @NotMapped
    private String password;
    private String surname;
    private String name;
    private String lastname;
    private String note;
    private Date birthday;
    private Long version;

    @NotMapped
    private List<EmployeeMetadataElementDto> metadata = Collections.EMPTY_LIST;

    @NotMapped
    private Long postId;

}
