package com.cssru.cncompanies.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cssru.cncompanies.dao.HumanDAO;
import com.cssru.cncompanies.dao.LoginDAO;
import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.domain.Login;
import com.cssru.cncompanies.domain.Task;
import com.cssru.cncompanies.domain.Unit;
import com.cssru.cncompanies.dto.AccountDto;
import com.cssru.cncompanies.exception.AccessDeniedException;
import com.cssru.cncompanies.service.CompanyService;
import com.cssru.cncompanies.service.HumanService;
import com.cssru.cncompanies.service.LoginService;
import com.cssru.cncompanies.service.TaskService;

@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	private CompanyService companyService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private HumanService humanService;

	@Autowired
	private LoginDAO loginDAO;

	@Autowired
	private HumanDAO humanDAO;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Transactional
	@Override
	public Login createLogin(AccountDto accountDto) {
		return createLogin(accountDto, null);
	}
	
	@Transactional
	@Override
	public Login createLogin(AccountDto accountDto, Unit unit) {
		Login newLogin = new Login();
		newLogin.setLogin(accountDto.getLogin());
		newLogin.setPassword(accountDto.getPassword());
		newLogin.setEmail(accountDto.getEmail());
		
		Human newHuman = new Human();
		newHuman.setSurname(accountDto.getSurname());
		newHuman.setName(accountDto.getName());
		newHuman.setLastName(accountDto.getLastName());
		newHuman.setNote(accountDto.getNote());
		newHuman.setBirthday(accountDto.getBirthday());
		
		if (unit != null) {
			newHuman.setUnit(unit);
		}
		
		newLogin.setHuman(newHuman);
		addLogin(newLogin); // password encrypts here
		return newLogin;
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
