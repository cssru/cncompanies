package com.cssru.cncompanies;

import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.cssru.cncompanies.service.CompanyService;
import com.cssru.cncompanies.service.HumanService;
import com.cssru.cncompanies.service.LoginService;
import com.cssru.cncompanies.service.TaskService;
import com.cssru.cncompanies.service.UnitService;

@Configuration
public class TestConfig {
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("credentials", "messages");
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}
	
	@Bean
	public LoginService loginService() {
		return Mockito.mock(LoginService.class);
	}

	@Bean
	public HumanService humanService() {
		return Mockito.mock(HumanService.class);
	}
	
	@Bean
	public CompanyService companyService() {
		return Mockito.mock(CompanyService.class);
	}

	@Bean
	public UnitService unitService() {
		return Mockito.mock(UnitService.class);
	}

	@Bean
	public TaskService taskService() {
		return Mockito.mock(TaskService.class);
	}

}
