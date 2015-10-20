package com.cssru.companies.dao.impl;

import com.cssru.companies.dao.AccountDao;
import com.cssru.companies.domain.Account;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountDaoImpl implements AccountDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void create(Account account) {
        sessionFactory
                .getCurrentSession()
                .save(account);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Account get(String login) {
        return (Account) sessionFactory
                .getCurrentSession()
                .createQuery("from Account where login = :login")
                .setParameter("login", login)
                .uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Account get(Long id) {
        return sessionFactory
                .getCurrentSession()
                .get(Account.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Account> list() {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Account")
                .list();
    }

    @Override
    public void update(Account account) {
        sessionFactory.getCurrentSession().update(account);
    }

    @Override
    public void delete(Long id) {
        Account persistentAccount = sessionFactory.getCurrentSession().get(Account.class, id);
        if (persistentAccount != null) {
            sessionFactory.getCurrentSession().delete(persistentAccount);
        }
    }

}
