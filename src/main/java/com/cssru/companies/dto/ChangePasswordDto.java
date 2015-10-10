package com.cssru.companies.dto;

import com.cssru.companies.annotation.MappedAs;
import com.cssru.companies.annotation.NotMapped;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordDto extends Dto {

    @MappedAs(propertyName = "login")
    private String loginName;

    @NotMapped
    private String oldPassword;

    @MappedAs(propertyName = "password")
    private String newPassword;

    @NotMapped
    private String newPassword2;

}
