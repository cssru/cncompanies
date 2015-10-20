package com.cssru.companies.dao.impl;

import com.cssru.companies.dao.TaskDao;
import com.cssru.companies.domain.Post;
import com.cssru.companies.domain.Task;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskDaoImpl implements TaskDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void create(Task task) {
        sessionFactory.getCurrentSession().save(task);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task get(Long id) {
        return (Task) sessionFactory.getCurrentSession().get(Task.class, id);
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<Task> listDone(Post executor) {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Task as t where t.executor = :executor and t.done not null and t.archive = false")
                .setParameter("executor", executor)
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Task> listUndone(Post executor) {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Task as t where t.executor = :executor and t.done is null and t.archive = false")
                .setParameter("executor", executor)
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Task> listAll(Post executor) {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Task as t where t.executor = :executor and t.archive = false")
                .setParameter("executor", executor)
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Task> listArchive(Post executor) {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Task as t where t.executor = :executor and t.archive = true")
                .setParameter("executor", executor)
                .list();
    }

    @Override
    public void update(Task task) {
        sessionFactory.getCurrentSession().update(task);
    }

    @Override
    public void delete(Long id) {
        Object persistentObject = sessionFactory
                .getCurrentSession()
                .get(Task.class, id);

        if (persistentObject != null) {
            sessionFactory
                    .getCurrentSession()
                    .delete(persistentObject);
        }
    }

}
