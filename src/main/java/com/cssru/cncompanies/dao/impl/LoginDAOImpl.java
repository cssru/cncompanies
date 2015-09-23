package com.cssru.cncompanies.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cssru.cncompanies.config.Scheduler;
import com.cssru.cncompanies.dao.LoginDAO;
import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.domain.Login;

@Repository
public class LoginDAOImpl implements LoginDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addLogin(Login login) {
		login.setCreateTime(new Date());
		if (login.getPaidTill() == null)
			login.setPaidTill(new Date(System.currentTimeMillis() + Scheduler.ONE_DAY*30L));
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(login);
	}

	@Override
	public void removeLogin(Login login) {
		Login existingLogin = (Login) sessionFactory.getCurrentSession().load(
				Login.class, login.getId());
		if (existingLogin != null) {
			sessionFactory.getCurrentSession().delete(login);
		}
	}

	@Override
	public void removeExpiredLogins(Long currentTime) {
		Query query = sessionFactory.getCurrentSession().
				createQuery("delete from Login as l where l.createTime < :deadTime and l.confirmCode is not null");
		query.setParameter("deadTime", new Date(currentTime-Scheduler.ONE_DAY));
		query.executeUpdate();
	}

	@Override
	public void updateLogin(Login login) {
		// TODO
		sessionFactory.getCurrentSession().merge(login);		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Login getLogin(String login) {
		Query query = sessionFactory.getCurrentSession().
				createQuery("from Login where login = :login");
		query.setParameter("login", login);
		List<Login> result = query.list();
		if (result != null && !result.isEmpty()) return result.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Login getLogin(Long id) {
		Query query = sessionFactory.getCurrentSession().
				createQuery("from Login as l where l.id = :id");
		query.setParameter("id", id);
		List<Login> result = query.list();
		if (result != null && !result.isEmpty()) return result.get(0);
		return null;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Login getLogin(Human human) {
		Query query = sessionFactory.getCurrentSession().
				createQuery("from Login where human = :human");
		query.setParameter("human", human);
		List<Login> result = query.list();
		if (result != null && !result.isEmpty()) return result.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Login> admListUser() {
		return sessionFactory
				.getCurrentSession()
				.createQuery("from Login as l where l.login != 'admin' and l.human.unit is null")
				.list();
	}

	@Override
	public Long getEmployeesCount(Login login) {
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery("select count(*) from Human as h where h.unit.company.owner = :human");
		query.setParameter("human", login.getHuman());
		return (Long) query.uniqueResult();
	}

}
