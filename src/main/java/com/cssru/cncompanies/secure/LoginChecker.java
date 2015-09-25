package com.cssru.cncompanies.secure;

import com.cssru.cncompanies.domain.Login;
import com.cssru.cncompanies.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LoginChecker {
	@Autowired
	private AccountService accountService;
	

	public void checkLogin(Login login) throws BadLoginException {
		if (login == null) throw new BadLoginException(BadLoginException.ACCESS_DENIED, "Вход в систему не осуществлен");
		if (hasRole(Role.ADMIN)) return;
		
		Login managerLogin;
		if (hasRole(Role.COMPANY_MANAGER)) {
			managerLogin = login;
		} else {
			managerLogin = accountService.getLogin(login.getHuman().getUnit().getCompany().getOwner());
			if (managerLogin == null) throw new BadLoginException(BadLoginException.ACCESS_DENIED, "Аккаунт руководителя компании не существует");
		}
		if (managerLogin.getLocked()) throw new BadLoginException(BadLoginException.LOGIN_LOCKED, "Аккаунт заблокирован");
		if (managerLogin.getPaidTill().getTime() < System.currentTimeMillis()) {
			throw new BadLoginException(BadLoginException.LOGIN_EXPIRED, "Истекло время действия аккаунта");
		}
		if (!managerLogin.isConfirmed()) {
			throw new BadLoginException(BadLoginException.LOGIN_NOT_CONFIRMED, "Логин не подтвержден");
		}
	}
	
	public Login getRegisteredLogin() {
		return accountService.getLogin(SecurityContextHolder.getContext().getAuthentication().getName());
	}
	
	public boolean hasRole(Role role) {
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new HumanGrantedAuthority(role));
	}
}
