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
	public void addUnit(Unit unit) {
		sessionFactory.getCurrentSession().save(unit);		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Unit> listUnit(Company company) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Unit where company = :company");
		query.setParameter("company", company);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Unit> listUnitsWithOwner(Human unitOwner) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Unit as u where u.owner = :owner");
		query.setParameter("owner", unitOwner);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Unit> listAllUnits(Human manager) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Unit as u where u.company.owner = :manager");
		query.setParameter("manager", manager);
		return query.list();
	}

	@Override
	public void removeUnit(Unit unit) {
		sessionFactory.getCurrentSession().delete(unit);
	}

	@Override
	public void updateUnit(Unit unit) {
		sessionFactory.getCurrentSession().merge(unit);
	}

	@Override
	public Unit getUnit(Long id) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Unit as u where u.id = :id");
		query.setParameter("id", id);
		return (Unit) query.uniqueResult();
	}

}
