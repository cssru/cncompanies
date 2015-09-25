package com.cssru.cncompanies.service.impl;

import java.util.List;

import com.cssru.cncompanies.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cssru.cncompanies.dao.HumanDAO;
import com.cssru.cncompanies.dao.AccountDAO;
import com.cssru.cncompanies.domain.Login;
import com.cssru.cncompanies.dto.AccountDto;
import com.cssru.cncompanies.exception.AccessDeniedException;
import com.cssru.cncompanies.service.CompanyService;
import com.cssru.cncompanies.service.HumanService;
import com.cssru.cncompanies.service.AccountService;
import com.cssru.cncompanies.service.TaskService;

@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private CompanyService companyService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private HumanService humanService;

	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	private HumanDAO humanDAO;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Transactional
	@Override
	public Account createAccount(AccountDto accountDto) {
		return createAccount(accountDto, null);
	}
	
	@Transactional
	@Override
	public Account createAccount(AccountDto accountDto, Unit unit) {
		Account account = new Account();
		account.setLogin(accountDto.getLogin());
		account.setPassword(accountDto.getPassword());
		account.setEmail(accountDto.getEmail());
		
		Human human = new Human();
		human.setSurname(accountDto.getSurname());
		human.setName(accountDto.getName());
		human.setLastName(accountDto.getLastName());
		human.setNote(accountDto.getNote());
		human.setBirthday(accountDto.getBirthday());
		
		if (unit != null) {
			human.setUnit(unit);
		}
		
		account.setHuman(human);
		add(account); // password encrypts here
		return account;
	}

	@Transactional (readOnly = true)
	@Override
	public Login getLogin(String userName) {
		return loginDAO.getLogin(userName);
	}

	@Transactional (readOnly = true)
	@Override
	public Login getLogin(Long id) {
		return loginDAO.getLogin(id);
	}

	@Transactional
	private void addLogin(Login login) {
		login.setPassword(passwordEncoder.encode(login.getPassword()));
		humanDAO.addHuman(login.getHuman());
		loginDAO.addLogin(login);
	}

	@Transactional
	@Override
	public void removeLogin(Login login) {
		loginDAO.removeLogin(login);
	}

	@Transactional
	@Override
	public void removeUser(Login login) throws AccessDeniedException {
		List<Company> companies = companyService.listCompany(login);
		for (Company c : companies) companyService.removeCompany(c.getId(), login);
		// remove user's own tasks
		List<Task> tasks = taskService.listTaskWithAuthor(login.getHuman(), login);
		for (Task t : tasks) taskService.removeTask(t.getId(), login);
		Human human = login.getHuman();
		loginDAO.removeLogin(login);
		humanDAO.removeHuman(human);
	}

	@Transactional
	@Override
	public void removeExpiredLogins(Long currentTime) {
		loginDAO.removeExpiredLogins(currentTime);
		humanDAO.removeHumansWithoutLogins();
	}

	@Transactional
	@Override
	public void updateLogin(Login login, boolean passwordUpdate) throws AccessDeniedException {
		if (passwordUpdate) {
			login.setPassword(passwordEncoder.encode(login.getPassword()));
		}
		humanService.updateHuman(login.getHuman(), login);
		loginDAO.updateLogin(login);
	}


	@Transactional (readOnly = true)
	@Override
	public Login getLogin(Human human) {
		// TODO 
		return loginDAO.getLogin(human);
	}

	@Transactional (readOnly = true)
	@Override
	public List<Login> admListUser() {
		return loginDAO.admListUser();
	}

	@Transactional (readOnly = true)
	@Override
	public Long getEmployeesCount(Login login) {
		return loginDAO.getEmployeesCount(login);
	}

}
