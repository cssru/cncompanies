package com.cssru.cncompanies.dao.impl;

import com.cssru.cncompanies.dao.AccountDAO;
import com.cssru.cncompanies.domain.Account;
import com.cssru.cncompanies.domain.Human;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class AccountDAOImpl implements AccountDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void create(Account account) {
		sessionFactory
				.getCurrentSession()
				.save(account);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Account get(String login) {
		return (Account)sessionFactory
				.getCurrentSession()
				.createQuery("from Account where login = :login")
				.setParameter("login", login)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Account get(Long id) {
		return (Account)sessionFactory.getCurrentSession()
				.createQuery("from Account as l where l.id = :id")
				.setParameter("id", id)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Account get(Human human) {
		return (Account)sessionFactory.getCurrentSession()
				.createQuery("from Account where human = :human")
				.setParameter("human", human)
				.uniqueResult();
	}

	@Override
	public void delete(Long id) {
		Account persistentAccount = (Account) sessionFactory.getCurrentSession().load(Account.class, id);
		if (persistentAccount != null) {
			sessionFactory.getCurrentSession().delete(persistentAccount);
		}
	}

	@Override
	public void update(Account account) {
		sessionFactory.getCurrentSession().merge(account);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Account> list() {
		return sessionFactory
				.getCurrentSession()
				.createQuery("from Account")
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Account> getExpired(Date date) {
		return sessionFactory
				.getCurrentSession()
				.createQuery("from Account where expired < :date")
				.setParameter("date", date)
				.list();
	}

	@Override
	public Long getEmployeesCount(Account account) {
		return (Long)sessionFactory
				.getCurrentSession()
				.createQuery("select count(*) from Human as h where h.unit.company.owner = :human")
                .setParameter("human", account.getHuman())
                .uniqueResult();
	}

}
