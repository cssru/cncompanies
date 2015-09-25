package com.cssru.cncompanies.service.impl;

import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Login;
import com.cssru.cncompanies.secure.Role;
import com.cssru.cncompanies.domain.Unit;
import com.cssru.cncompanies.exception.AccessDeniedException;
import com.cssru.cncompanies.secure.HumanGrantedAuthority;
import com.cssru.cncompanies.service.CompanyService;
import com.cssru.cncompanies.service.AccountService;
import com.cssru.cncompanies.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private AccountService accountService;

	@Autowired
	private UnitService unitService;

	@Autowired
	private CompanyService companyService;

	@Override
	public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
		Login login = accountService.getLogin(loginName);
		if (login == null) throw new UsernameNotFoundException("Пользователь "+loginName+" не найден");
		Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();

		List<Company> ownedCompanies = companyService.listCompany(login);
		// если пользователь является администратором
		if (login.getLogin().equalsIgnoreCase("admin")) {
			roles.add(new HumanGrantedAuthority(Role.ADMIN));
		} else 
		// пользователь является владельцем компании, если ему принадлежит хотя бы одна компания
		if ((ownedCompanies != null && !ownedCompanies.isEmpty()) ||
				// или если он не принадлежит ни к одному подразделению
				// (только что зарегистрировался и еще не создал ни одной компании)
				(login.getHuman().getUnit() == null)) {
			roles.add(new HumanGrantedAuthority(Role.COMPANY_MANAGER));
		} else {
			// если пользователь не владелец компании, то он может быть начальником подразделения
			try {
				List<Unit> ownedUnits = unitService.listUnitsWithOwner(login.getHuman(), login);
				if (ownedUnits != null && !ownedUnits.isEmpty()) {
					roles.add(new HumanGrantedAuthority(Role.UNIT_MANAGER));
				} else {
					// иначе он - простой пользователь
					roles.add(new HumanGrantedAuthority(Role.USER));
				}
			} catch (AccessDeniedException ade) {
				roles.add(new HumanGrantedAuthority(Role.USER));
			}
		}
		UserDetails userDetails =
				new User(login.getLogin(),
						login.getPassword(),
						roles);

		return userDetails;
	}

}
