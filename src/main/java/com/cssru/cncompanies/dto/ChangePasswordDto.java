package com.cssru.cncompanies.dto;

import com.cssru.cncompanies.domain.Login;

public class ChangePasswordDto {
	private String loginName;
	private String oldPassword;
	private String newPassword;
	private String newPassword2;
	public ChangePasswordDto() {
		loginName = "";
		oldPassword = "";
		newPassword = "";
		newPassword2 = "";
	}

	public ChangePasswordDto(Login login) {
		this.loginName = login.getLogin(); 
	}

	public String getLoginName() {
		return loginName;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public String getNewPassword2() {
		return newPassword2;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public void setNewPassword2(String newPassword2) {
		this.newPassword2 = newPassword2;
	}
}
