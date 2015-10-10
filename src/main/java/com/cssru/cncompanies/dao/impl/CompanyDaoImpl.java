package com.cssru.cncompanies.dao.impl;

import com.cssru.cncompanies.dao.CompanyDao;
import com.cssru.cncompanies.domain.Account;
import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Employee;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class CompanyDaoImpl implements CompanyDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Company company) {
        sessionFactory.getCurrentSession().save(company);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Company> listByManager(Employee manager) {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Company where manager = :manager")
                .setParameter("manager", manager)
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Company> listByHolder(Account holder) {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Company where holder = :holder")
                .setParameter("holder", holder)
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
        return (Company) sessionFactory.getCurrentSession()
                .createQuery("from Company as c where c.id = :id")
                .setParameter("id", id)
                .uniqueResult();
    }

}
