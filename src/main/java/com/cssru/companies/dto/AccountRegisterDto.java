package com.cssru.companies.dto;

import com.cssru.companies.annotation.Email;
import com.cssru.companies.domain.Account;
import com.cssru.companies.domain.Unit;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class AccountRegisterDto extends Dto {

    private Long id;

    @NotNull
    @NotEmpty
    private String login;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String passwordConfirm;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotEmpty
    private String surname;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String lastname;

    private String note;
    private Date birthday;
    private Long unitId;

    public AccountRegisterDto() {
    }

    public AccountRegisterDto(Account account) {
        this.id = account.getEmployee().getId();
        this.login = account.getLogin();
        this.surname = account.getEmployee().getSurname();
        this.name = account.getEmployee().getName();
        this.lastname = account.getEmployee().getLastname();
        this.note = account.getEmployee().getNote();
        this.birthday = account.getEmployee().getBirthday();
        Unit unit = account.getEmployee().getUnit();
        if (unit == null) {
            this.unitId = 0L;
        } else {
            this.unitId = unit.getId();
        }
    }

}
