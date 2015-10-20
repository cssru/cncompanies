package com.cssru.companies.dao.impl;

import com.cssru.companies.dao.StaffDao;
import com.cssru.companies.domain.Staff;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class StaffDaoImpl implements StaffDao {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void create(Staff staff) {
        sessionFactory
                .getCurrentSession()
                .save(staff);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Staff get(Long id) {
        return sessionFactory
                .getCurrentSession()
                .get(Staff.class, id);
    }

    @Override
    public void update(Staff staff) {
        sessionFactory.getCurrentSession().update(staff);
    }

    @Override
    public void delete(Long id) {
        Staff persistentStaff = sessionFactory.getCurrentSession().get(Staff.class, id);
        if (persistentStaff != null) {
            sessionFactory.getCurrentSession().delete(persistentStaff);
        }
    }
}
