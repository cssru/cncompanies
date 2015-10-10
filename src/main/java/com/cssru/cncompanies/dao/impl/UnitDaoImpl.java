package com.cssru.cncompanies.dao.impl;

import com.cssru.cncompanies.dao.CompanyDao;
import com.cssru.cncompanies.dao.HumanDao;
import com.cssru.cncompanies.dao.UnitDao;
import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Employee;
import com.cssru.cncompanies.domain.Unit;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UnitDaoImpl implements UnitDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    CompanyDao companyDao;

    @Autowired
    HumanDao humanDao;

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
    public List<Unit> list(Employee unitManager) {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Unit where manager = :manager")
                .setParameter("manager", unitManager)
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Unit> listVisible(Employee manager) {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Unit where company.manager = :manager or manager = :manager")
                .setParameter("manager", manager)
                .list();
    }

    @Override
    public void delete(Unit unit) {
        sessionFactory.getCurrentSession().delete(unit);
    }

    @Override
    public void update(Unit unit) {
        sessionFactory.getCurrentSession().merge(unit);
    }

    @Override
    public Unit get(Long id) {
        return (Unit) sessionFactory
                .getCurrentSession()
                .createQuery("from Unit as u where u.id = :id")
                .setParameter("id", id)
                .uniqueResult();
    }

}
