package com.cssru.companies.dao.impl;

import com.cssru.companies.dao.EmployeeDao;
import com.cssru.companies.domain.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void create(Employee employee) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(employee);
    }

    @Override
    public void delete(Long id) {
        Employee persistentEmployee = sessionFactory
                .getCurrentSession()
                .get(Employee.class, id);
        if (persistentEmployee != null) {
            // it also deletes associated HumanMetadataElements
            sessionFactory
                    .getCurrentSession()
                    .delete(persistentEmployee);
        }
    }

    @Override
    public void update(Employee employee) {
        sessionFactory.getCurrentSession().update(employee);
    }

    @Override
    public Employee get(Long id) {
        return sessionFactory
                .getCurrentSession()
                .get(Employee.class, id);
    }

    @Override
    public Employee get(String login) {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Employee where login = :login")
                .setParameter("login", login)
                .uniqueResult();
    }

}
