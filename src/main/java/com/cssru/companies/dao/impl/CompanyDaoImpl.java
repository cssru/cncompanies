package com.cssru.companies.dao.impl;

import com.cssru.companies.dao.CompanyDao;
import com.cssru.companies.domain.Account;
import com.cssru.companies.domain.Company;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class CompanyDaoImpl implements CompanyDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void create(Company company) {
        sessionFactory.getCurrentSession().save(company);
    }

    @Override
    public Company get(Long id) {
        return sessionFactory
                .getCurrentSession()
                .get(Company.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Company> list(Account holder) {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Company where holder = :holder")
                .setParameter("holder", holder)
                .list();
    }

    @Override
    public void update(Company company) {
        sessionFactory.getCurrentSession().update(company);
    }

    @Override
    public void delete(Long id) {
        Company persistedCompany = sessionFactory
                .getCurrentSession()
                .get(Company.class, id);
        if (persistedCompany != null) {
            sessionFactory
                    .getCurrentSession()
                    .delete(persistedCompany);
        }
    }
}
