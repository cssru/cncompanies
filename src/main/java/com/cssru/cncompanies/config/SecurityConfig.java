package com.cssru.cncompanies.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cssru.cncompanies.secure.Role;
import com.cssru.cncompanies.service.impl.UserDetailsServiceImpl;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	// регистрируем нашу реализацию UserDetailsService
	// а также PasswordEncoder
	@Autowired
	public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.userDetailsService(userDetailsService)
		.passwordEncoder(getPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.antMatchers("/resources/**", "/**").permitAll()
		.antMatchers("/", "/login", "/registration").permitAll()
		.antMatchers("/company.*").hasRole(Role.COMPANY_MANAGER.name())
		.antMatchers("/unit.*").hasRole(Role.COMPANY_MANAGER.name())
		.antMatchers("/human.*").hasAnyRole(Role.COMPANY_MANAGER.name(), Role.UNIT_MANAGER.name())
		.antMatchers("/admin/*").hasRole(Role.ADMIN.name());

		http.formLogin()
		.loginPage("/")
		.loginProcessingUrl("/j_spring_security_check")
		.failureUrl("/badlogin")
		.usernameParameter("j_username")
		.passwordParameter("j_password")
		.permitAll();

		http.logout()
		.permitAll()
		.logoutUrl("/logout")
		.logoutSuccessUrl("/")
		.invalidateHttpSession(true);

	}

	@Bean
	public BCryptPasswordEncoder getPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService(){
		return new UserDetailsServiceImpl();
	}

}
