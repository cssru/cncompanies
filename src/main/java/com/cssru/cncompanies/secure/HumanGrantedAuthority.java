package com.cssru.cncompanies.secure;

import org.springframework.security.core.GrantedAuthority;

import com.cssru.cncompanies.domain.Role;

public class HumanGrantedAuthority implements GrantedAuthority {
	private static final long serialVersionUID = 1L;
	private Role role;
	
	public HumanGrantedAuthority(Role role) {
		this.role = role;
	}
	
	@Override
	public String getAuthority() {
		return role.name();
	}
	
	public Role getRole() {
		return role;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof GrantedAuthority)) return false;
		GrantedAuthority arg2 = (GrantedAuthority)o;
		return this.getAuthority().equals(arg2.getAuthority());
	}
}
