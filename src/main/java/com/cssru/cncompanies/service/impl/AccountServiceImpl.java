package com.cssru.cncompanies.service.impl;

import java.util.Date;
import java.util.List;

import com.cssru.cncompanies.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	PasswordEncoder passwordEncoder;

	@Transactional
	@Override
	public Account create(AccountDto accountDto) {
		return create(accountDto, null);
	}

	@Transactional
	@Override
	public Account create(AccountDto accountDto, Unit unit) {
		Account account = new Account();
		account.setLogin(accountDto.getLogin());
		account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
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
		humanDAO.add(human);
		accountDAO.add(account);

		return account;
	}

	@Transactional (readOnly = true)
	@Override
	public Account get(String userName) {
		return accountDAO.get(userName);
	}

	@Transactional (readOnly = true)
	@Override
	public Account get(Long id) {
		return accountDAO.get(id);
	}

	@Transactional (readOnly = true)
	@Override
	public Account get(Human human) {
		return accountDAO.get(human);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		accountDAO.delete(id);
	}

	@Transactional
	@Override
	public void removeExpired(Date now) {
		List <Account> expired = accountDAO.getExpired(now);
        for (Account account:expired) {
            accountDAO.delete(account.getId());
        }
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
	public List<Login> admListUser() {
		return loginDAO.admListUser();
	}

	@Transactional (readOnly = true)
	@Override
	public Long getEmployeesCount(Login login) {
		return loginDAO.getEmployeesCount(login);
	}

}
