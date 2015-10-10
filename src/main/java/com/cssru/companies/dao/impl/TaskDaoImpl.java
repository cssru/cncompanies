package com.cssru.companies.dao.impl;

import com.cssru.companies.dao.TaskDao;
import com.cssru.companies.domain.Employee;
import com.cssru.companies.domain.Task;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class TaskDaoImpl implements TaskDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(Task task) {
        sessionFactory.getCurrentSession().save(task);
    }

    @Override
    public void update(Task task) {
        sessionFactory.getCurrentSession().merge(task);
    }

    @Override
    public void delete(Long id) {
        Object persistentObject = sessionFactory
                .getCurrentSession()
                .load(Task.class, id);

        if (persistentObject != null) {
            sessionFactory
                    .getCurrentSession()
                    .delete(persistentObject);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task get(Long id) {
        return (Task) sessionFactory.getCurrentSession().get(Task.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Task> list(long lastModified) {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Task as t where t.lastModified > :lastModified")
                .setParameter("lastModified", new Date(lastModified))
                .list();
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<Task> listDone(Employee executor) {// select only done tasks not from archive
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Task as t where t.executor = :executor and t.done not null and t.archive = false")
                .setParameter("executor", executor)
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Task> listUndone(Employee executor) {// select only undone tasks not from archive
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Task as t where t.executor = :executor and t.done is null and t.archive = false")
                .setParameter("executor", executor)
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Task> listAll(Employee executor) {// select all tasks not from archive
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Task as t where t.executor = :executor and t.archive = false")
                .setParameter("executor", executor)
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Task> listDoneForSlave(Employee manager) {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Task as t " +
                        "where (t.executor.unit.company.owner = :manager or t.executor.unit.manager = :manager) " +
                        "and (t.done not null and t.archive = false)")
                .setParameter("manager", manager)
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Task> listUndoneForSlave(Employee manager) {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Task as t " +
                        "where (t.executor.unit.company.owner = :manager or t.executor.unit.manager = :manager) " +
                        "and (t.done is null and t.archive = false)")
                .setParameter("manager", manager)
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Task> listDoneVisible(Employee manager) {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Task as t " +
                        "where (t.author = :manager or t.executor.unit.company.owner = :manager or t.executor.unit.manager = :manager) " +
                        "and (t.done not null and t.archive = false)")
                .setParameter("manager", manager)
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Task> listUndoneVisible(Employee manager) {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Task as t " +
                        "where (t.author = :manager or t.executor.unit.company.owner = :manager or t.executor.unit.manager = :manager) " +
                        "and (t.done is null and t.archive = false)")
                .setParameter("manager", manager)
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Task> listDoneWithAuthor(Employee author) {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Task as t where t.author = :author and t.archive = false and t.done not null")
                .setParameter("author", author)
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Task> listUndoneWithAuthor(Employee author) {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Task as t where t.author = :author and t.archive = false and t.done is null")
                .setParameter("author", author)
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Task> listArchive(Employee executor) {// select only tasks from archive for gived executor
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Task as t where t.executor = :executor and t.archive = true")
                .setParameter("executor", executor)
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Task> listArchiveWithAuthor(Employee author) {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Task as t where t.author = :author and t.archive = true")
                .setParameter("author", author)
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Task> listArchiveForSlave(Employee manager) {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Task as t " +
                        "where (t.executor.unit.company.owner = :manager or t.executor.unit.manager = :manager) " +
                        "and t.archive = true")
                .setParameter("manager", manager)
                .list();
    }


    // statistics
    @Override
    public Long getNormalCount(Employee executor) {
        return (Long) sessionFactory
                .getCurrentSession()
                .createQuery("select count(*) from Task as t where t.executor = :executor and t.archive = false and "
                        + "t.expires > :now and (t.expires - t.alertTime) > :now")
                .setParameter("executor", executor)
                .setParameter("now", new Date())
                .uniqueResult();
    }

    @Override
    public Long getExpiredCount(Employee executor) {
        return (Long) sessionFactory
                .getCurrentSession()
                .createQuery("select count(*) from Task as t where t.executor = :executor and t.archive = false and "
                        + "t.expires <= :now")
                .setParameter("executor", executor)
                .setParameter("now", new Date())
                .uniqueResult();
    }

    @Override
    public Long getNearestCount(Employee executor) {
        return (Long) sessionFactory
                .getCurrentSession()
                .createQuery("select count(*) from Task as t where t.executor = :executor and t.archive = false and "
                        + "t.expires > :now and :now >= (t.expires - t.alertTime)")
                .setParameter("executor", executor)
                .setParameter("now", new Date())
                .uniqueResult();
    }

    @Override
    public Long getDoneCount(Employee executor) {
        return (Long) sessionFactory
                .getCurrentSession()
                .createQuery("select count(*) from Task as t where t.executor = :executor and t.archive = false and t.done not null")
                .setParameter("executor", executor)
                .uniqueResult();
    }

    @Override
    public Long getArchiveCount(Employee executor) {
        return (Long) sessionFactory
                .getCurrentSession()
                .createQuery("select count(*) from Task as t where t.executor = :executor and t.archive = true")
                .setParameter("executor", executor)
                .uniqueResult();
    }

    // for ajax requests
    @Override
    public void setTaskContent(Long id, String content) {
        Task existingTask = get(id);
        if (existingTask != null) {
            existingTask.setContent(content);
            sessionFactory.getCurrentSession().update(existingTask);
        }
    }

    @Override
    public void setTaskComment(Long id, String comment) {
        Task existingTask = get(id);
        if (existingTask != null) {
            existingTask.setComment(comment);
            sessionFactory.getCurrentSession().update(existingTask);
        }
    }

}
