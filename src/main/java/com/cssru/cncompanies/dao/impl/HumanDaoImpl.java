package com.cssru.cncompanies.dao.impl;

import com.cssru.cncompanies.dao.HumanDao;
import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.domain.Unit;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HumanDaoImpl implements HumanDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(Human human) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(human);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Human> list(Unit unit) {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Human as h where h.unit = :unit")
                .setParameter("unit", unit)
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Human> list(Company company) {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Human as h where h.unit.company = :company")
                .setParameter("company", company)
                .list();
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<Human> list(long version, Human manager) {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Human as h where h.version > :version and (h.unit.owner = :manager or h.unit.company.owner = :manager)")
                .setParameter("version", version)
                .setParameter("manager", manager)
                .list();
    }

    @Override
    public void delete(Long id) {
        Object persistentObject = sessionFactory
                .getCurrentSession()
                .load(Human.class, id);
        if (persistentObject != null) {
            // it also deletes associated HumanMetadataElements
            sessionFactory
                    .getCurrentSession()
                    .delete(persistentObject);
        }
    }

    @Override
    public void delete(Human human) {
        sessionFactory
                .getCurrentSession()
                .delete(human);
    }

    @Override
    public void deleteWithoutLogins() {
        sessionFactory
                .getCurrentSession()
                .createQuery("delete from Human as h where h not in (select l.human from Login as l)")
                .executeUpdate();
    }

    @Override
    public void update(Human human) {
        sessionFactory.getCurrentSession().update(human);
    }

    @Override
    public Human get(Long id) {
        return (Human)sessionFactory
                .getCurrentSession()
                .createQuery("from Human as h where h.id = :id")
                .setParameter("id", id)
                .uniqueResult();
    }

}
