package com.cssru.companies.dto;

import com.cssru.companies.annotation.NotMapped;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AccountDto extends Dto {
    private Long id;
    private String login;

    @NotMapped
    private String password;

    @NotMapped
    private String passwordRepeat;

    private String surname;
    private String name;
    private String lastname;
    private String email;
    private Date created;
    private Date expires;
    private Boolean enabled;
    private Boolean locked;
}
