package com.cssru.companies.config;

import com.cssru.companies.secure.Role;
import com.cssru.companies.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


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
                .antMatchers("/company.*").hasRole(Role.ROLE_COMPANY_MANAGER.name())
                .antMatchers("/unit.*").hasRole(Role.ROLE_COMPANY_MANAGER.name())
                .antMatchers("/employee.*").hasAnyRole(Role.ROLE_COMPANY_MANAGER.name(), Role.ROLE_UNIT_MANAGER.name())
                .antMatchers("/admin/*").hasRole(Role.ROLE_ADMIN.name());

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
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

}
