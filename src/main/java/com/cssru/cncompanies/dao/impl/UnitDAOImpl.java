package com.cssru.cncompanies.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cssru.cncompanies.dao.CompanyDAO;
import com.cssru.cncompanies.dao.HumanDAO;
import com.cssru.cncompanies.dao.UnitDAO;
import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.domain.Unit;

@Repository
public class UnitDAOImpl implements UnitDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	CompanyDAO companyDAO;

	@Autowired
	HumanDAO humanDAO;

	@Override
	public void add(Unit unit) {
		sessionFactory.getCurrentSession().save(unit);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Unit> list(Company company) {
		return sessionFactory
				.getCurrentSession()
				.createQuery("from Unit where company = :company")
				.setParameter("company", company)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Unit> list(Human unitManager) {
		return sessionFactory
				.getCurrentSession()
				.createQuery("from Unit as u where u.manager = :manager")
				.setParameter("manager", unitManager)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Unit> listVisible(Human manager) {
		return sessionFactory
				.getCurrentSession()
				.createQuery("from Unit as u where u.company.owner = :manager or u.owner = :manager")
				.setParameter("manager", manager)
				.list();
	}

	@Override
	public void delete(Unit unit) {
		Unit persistentUnit = (Unit)sessionFactory
				.getCurrentSession()
				.load(Unit.class, unit.getId());
		if (persistentUnit != null)
			sessionFactory.getCurrentSession().delete(persistentUnit);
	}

	@Override
	public void update(Unit unit) {
		sessionFactory.getCurrentSession().merge(unit);
	}

	@Override
	public Unit get(Long id) {
		return (Unit)sessionFactory
				.getCurrentSession()
				.createQuery("from Unit as u where u.id = :id")
				.setParameter("id", id)
				.uniqueResult();
	}

}
