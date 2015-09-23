package com.cssru.cncompanies.config;

import com.cssru.cncompanies.service.HumanService;
import com.cssru.cncompanies.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {
	public static final long ONE_DAY = 1000*60*60*24; 
	
	@Autowired
	private LoginService loginService;

	@Autowired
	private HumanService humanService;

	public Scheduler() {}

	@Scheduled(fixedDelay=ONE_DAY)
	public void deleteExpiredAccounts() {
		loginService.removeExpiredLogins(System.currentTimeMillis());
	}
}
