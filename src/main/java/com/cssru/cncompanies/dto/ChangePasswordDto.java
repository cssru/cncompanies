package com.cssru.cncompanies.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordDto {
	private String loginName;
	private String oldPassword;
	private String newPassword;
	private String newPassword2;

}
