package com.cssru.cncompanies.dao.impl;

import com.cssru.cncompanies.dao.HumanDao;
import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Employee;
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
    public void add(Employee employee) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(employee);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Employee> list(Unit unit) {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Employee as h where h.unit = :unit")
                .setParameter("unit", unit)
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Employee> list(Company company) {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Employee as h where h.unit.company = :company")
                .setParameter("company", company)
                .list();
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<Employee> list(long version, Employee manager) {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Employee as h where h.version > :version and (h.unit.owner = :manager or h.unit.company.owner = :manager)")
                .setParameter("version", version)
                .setParameter("manager", manager)
                .list();
    }

    @Override
    public void delete(Long id) {
        Object persistentObject = sessionFactory
                .getCurrentSession()
                .load(Employee.class, id);
        if (persistentObject != null) {
            // it also deletes associated HumanMetadataElements
            sessionFactory
                    .getCurrentSession()
                    .delete(persistentObject);
        }
    }

    @Override
    public void delete(Employee employee) {
        sessionFactory
                .getCurrentSession()
                .delete(employee);
    }

    @Override
    public void deleteWithoutLogins() {
        sessionFactory
                .getCurrentSession()
                .createQuery("delete from Employee as h where h not in (select l.employee from Login as l)")
                .executeUpdate();
    }

    @Override
    public void update(Employee employee) {
        sessionFactory.getCurrentSession().update(employee);
    }

    @Override
    public Employee get(Long id) {
        return (Employee) sessionFactory
                .getCurrentSession()
                .createQuery("from Employee as h where h.id = :id")
                .setParameter("id", id)
                .uniqueResult();
    }

}
