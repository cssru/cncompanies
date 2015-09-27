package com.cssru.cncompanies.dao.impl;

import com.cssru.cncompanies.dao.CompanyDAO;
import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Human;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class CompanyDAOImpl implements CompanyDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(Company company) {
		sessionFactory.getCurrentSession().save(company);		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Company> list(Human owner) {
		return sessionFactory
				.getCurrentSession()
				.createQuery("from Company where owner = :owner")
				.setParameter("owner", owner)
				.list();
	}

	@Override
	public void delete(Company company) {
		sessionFactory.getCurrentSession().delete(company);
	}

	@Override
	public void update(Company company) {
		sessionFactory.getCurrentSession().update(company);
	}

	@Override
	public Company get(Long id) {
		return (Company)sessionFactory.getCurrentSession()
				.createQuery("from Company as c where c.id = :id")
				.setParameter("id", id)
				.uniqueResult();
	}

}
