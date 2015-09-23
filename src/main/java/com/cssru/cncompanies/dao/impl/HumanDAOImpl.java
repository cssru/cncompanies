package com.cssru.cncompanies.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cssru.cncompanies.dao.HumanDAO;
import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.domain.Unit;

@Repository
public class HumanDAOImpl implements HumanDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addHuman(Human human) {
		human.setLastModified(new Date());
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(human);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Human> listHuman(Unit unit) {
		return sessionFactory
				.getCurrentSession()
				.createQuery("from Human as h where h.unit = :unit")
				.setParameter("unit", unit)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Human> listHuman(Company company) {
		return sessionFactory
				.getCurrentSession()
				.createQuery("from Human as h where h.unit.company = :company")
				.setParameter("company", company)
				.list();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Human> listHuman(long lastModified, Human manager) {
		return sessionFactory
				.getCurrentSession()
				.createQuery("from Human as h where h.lastModified > :lastModified and (h.unit.owner = :manager or h.unit.company.owner = :manager)")
				.setParameter("lastModified", new Date(lastModified))
				.setParameter("manager", manager)
				.list();
	}

	@Override
	public void removeHuman(Human human) {
		Object persistentObject = sessionFactory
				.getCurrentSession()
				.load(Human.class, human.getId());
		if (persistentObject != null) {
			// it also deletes associated HumanMetadataElements
					sessionFactory
					.getCurrentSession()
					.delete(persistentObject);
		}
	}

	@Override
	public void removeHumansWithoutLogins() {
		sessionFactory
				.getCurrentSession()
				.createQuery("delete from Human as h where h not in (select l.human from Login as l)")
				.executeUpdate();
	}

	@Override
	public void updateHuman(Human human) {
		sessionFactory.getCurrentSession().merge(human);
	}

	@Override
	public Human getHuman(Long id) {
		return (Human)sessionFactory
				.getCurrentSession()
				.createQuery("from Human as h where h.id = :id")
				.setParameter("id", id)
				.uniqueResult();
	}

}
