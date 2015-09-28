package com.cssru.cncompanies.dto;

import com.cssru.cncompanies.annotation.Email;
import com.cssru.cncompanies.domain.Account;
import com.cssru.cncompanies.domain.Unit;
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
		this.id = account.getHuman().getId();
		this.login = account.getLogin();
		this.surname = account.getHuman().getSurname();
		this.name = account.getHuman().getName();
		this.lastname = account.getHuman().getLastname();
		this.note = account.getHuman().getNote();
		this.birthday = account.getHuman().getBirthday();
		Unit unit = account.getHuman().getUnit();
		if (unit == null) {
			this.unitId = 0L;
		} else {
			this.unitId = unit.getId();
		}
	}

}
