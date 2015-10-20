package com.cssru.companies.dao.impl;

import com.cssru.companies.dao.CompanyDao;
import com.cssru.companies.dao.EmployeeDao;
import com.cssru.companies.dao.UnitDao;
import com.cssru.companies.domain.Company;
import com.cssru.companies.domain.Unit;
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
    EmployeeDao employeeDao;

    @Override
    public void create(Unit unit) {
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

    @Override
    public void update(Unit unit) {
        sessionFactory.getCurrentSession().update(unit);
    }

    @Override
    public void delete(Long id) {
        Unit persistentUnit = sessionFactory
                .getCurrentSession()
                .get(Unit.class, id);
        if (persistentUnit != null) {
            sessionFactory.getCurrentSession().delete(persistentUnit);
        }
    }

    @Override
    public Unit get(Long id) {
        return sessionFactory
                .getCurrentSession()
                .get(Unit.class, id);
    }

}
