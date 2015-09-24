package com.cssru.cncompanies.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cssru.cncompanies.dao.TaskDAO;
import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.domain.Login;
import com.cssru.cncompanies.domain.Task;

@Repository
public class TaskDAOImpl implements TaskDAO {

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
		return (Task)sessionFactory.getCurrentSession().get(Task.class, id);
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
	public List<Task> listDone(Human executor) {// select only done tasks not from archive
		return sessionFactory
				.getCurrentSession()
				.createQuery("from Task as t where t.executor = :executor and t.done not null and t.archive = false")
				.setParameter("executor", executor)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Task> listUndone(Human executor) {// select only undone tasks not from archive
		return sessionFactory
				.getCurrentSession()
				.createQuery("from Task as t where t.executor = :executor and t.done is null and t.archive = false")
				.setParameter("executor", executor)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Task> listAll(Human executor) {// select all tasks not from archive
		return sessionFactory
				.getCurrentSession()
				.createQuery("from Task as t where t.executor = :executor and t.archive = false")
				.setParameter("executor", executor)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Task> listDoneForSlave(Human manager) {
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
	public List<Task> listArchive(Human executor) {// select only tasks from archive for gived executor
		return sessionFactory
				.getCurrentSession()
				.createQuery("from Task as t where t.executor = :executor and t.archive = true")
				.setParameter("executor", executor)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Task> listArchiveWithAuthor(Human author) {
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery("from Task as t where t.author = :author and t.archive = true");
		query.setParameter("author", author);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Task> listUndoneForSlave(Human manager) {
		ArrayList<Task> result = new ArrayList<Task>();
		Query query;
		List<Task> list;

		// зашел руководитель подразделения или руководитель компании
		// руководитель подразделения (руководитель компании) выбирает задачи, поставленные им самим исполнителю
		query = sessionFactory
				.getCurrentSession()
				.createQuery("from Task as t where (t.author = :manager or (t.owner.unit.company.owner = :manager and t.author <> t.owner)) "
						+ "and (t.done = 0 or t.done is null) and (t.archive = false or t.archive is null)");
		query.setParameter("manager", manager);
		list = query.list();
		for (Task t:list) {
			t.setReadonly(false);
		}
		result.addAll(list);

		// руководитель подразделения выбирает задачи, поставленные исполнителю руководителем компании, при этом он не может изменять их
		query = sessionFactory
				.getCurrentSession()
				.createQuery("from Task as t where t.author <> t.owner and t.author <> :manager "
						+ "and (t.done = 0 or t.done is null) and (t.archive = false or t.archive is null) and "
						+ "t.owner.unit.owner = :manager");
		query.setParameter("manager", manager);
		list = query.list();
		for (Task t:list) {
			t.setReadonly(true);
		}
		result.addAll(list);

		return result;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Task> listArchiveForSlave(Human manager) {
		ArrayList<Task> result = new ArrayList<Task>();
		Query query;
		List<Task> list;
		Human manager = managerLogin.getHuman();

		// зашел руководитель подразделения или руководитель компании
		// руководитель подразделения (руководитель компании) выбирает задачи, поставленные им самим исполнителю
		query = sessionFactory
				.getCurrentSession()
				.createQuery("from Task as t where (t.author = :manager or (t.owner.unit.company.owner = :manager and t.author <> t.owner)) "
						+ "and t.archive = true");
		query.setParameter("manager", manager);
		list = query.list();
		for (Task t:list) {
			t.setReadonly(false);
		}
		result.addAll(list);

		// руководитель подразделения выбирает задачи, поставленные исполнителю руководителем компании, при этом он не может изменять их
		query = sessionFactory
				.getCurrentSession()
				.createQuery("from Task as t where t.author <> t.owner and t.author <> :manager "
						+ "and t.archive = true and t.owner.unit.owner = :manager");
		query.setParameter("manager", manager);
		list = query.list();
		for (Task t:list) {
			t.setReadonly(true);
		}
		result.addAll(list);

		return result;
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<Task> listVisible(Human manager) {// выбираем только не выполненные задачи, которые поставлены подчиненным managerLogin'а
		ArrayList<Task> result = new ArrayList<Task>();
		Query query;
		List<Task> list;

		// когда manager является также и автором задачи - может изменять ее
		query = sessionFactory
				.getCurrentSession()
				.createQuery("from Task as t where (t.author = :manager or (t.author = t.owner.unit.owner and t.owner.unit.company.owner = :manager)) "
						+ "and (t.archive = false or t.archive is null)");
		query.setParameter("manager", manager);
		list = query.list();
		for (Task t:list) {
			t.setReadonly(false);
		}
		result.addAll(list);

		// задачи, поставленные подчиненным manager'a руководителем компании
		query = sessionFactory
				.getCurrentSession()
				.createQuery("from Task as t where t.author <> t.owner and t.author <> :manager and t.owner.unit.owner = :manager "
						+ "and (t.archive = false or t.archive is null)");
		query.setParameter("manager", manager);
		list = query.list();
		for (Task t:list) {
			t.setReadonly(true);
		}
		result.addAll(list);

		return result;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Task> listWithAuthor(Human author) {
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery("from Task as t where t.author = :author and (t.archive = false or t.archive is null) and "
						+ "(((t.owner.unit.owner = :manager or t.owner.unit.company.owner = :manager) and t.author <> t.owner) or t.author = :manager)");
		query.setParameter("author", author);
		return query.list();
	}




	// statistic
	@Override
	public Long getNormalCount(Human executor) {
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery("select count(*) from Task as t where t.owner = :owner and (t.archive = false or t.archive is null) and "
						+ "(((t.owner.unit.owner = :manager or t.owner.unit.company.owner = :manager) and t.author <> t.owner) or t.author = :manager) and "
						+ "(t.expires > :now and (t.expires - t.alertTime) > :now)");
		query.setParameter("owner", executor);
		query.setParameter("now", new Date());
		return (Long)query.uniqueResult();
	}

	@Override
	public Long getExpiredCount(Human executor) {
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery("select count(*) from Task as t where t.owner = :owner and (t.archive = false or t.archive is null) and "
						+ "(((t.owner.unit.owner = :manager or t.owner.unit.company.owner = :manager) and t.author <> t.owner) or t.author = :manager) and "
						+ "(t.expires <= :now)");
		query.setParameter("owner", executor);
		query.setParameter("manager", managerLogin.getHuman());
		query.setParameter("now", new Date());
		return (Long)query.uniqueResult();
	}

	@Override
	public Long getNearestCount(Human executor) {
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery("select count(*) from Task as t where t.owner = :owner and (t.archive = false or t.archive is null) and "
						+ "(((t.owner.unit.owner = :manager or t.owner.unit.company.owner = :manager) and t.author <> t.owner) or t.author = :manager) and "
						+ "(t.expires > :now and :now >= (t.expires - t.alertTime))");
		query.setParameter("owner", executor);
		query.setParameter("now", new Date());
		return (Long)query.uniqueResult();
	}

	@Override
	public Long getDoneCount(Human executor) {
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery("select count(*) from Task as t where t.owner = :owner and (t.archive = false or t.archive is null) and "
						+ "(((t.owner.unit.owner = :manager or t.owner.unit.company.owner = :manager) and t.author <> t.owner) or t.author = :manager) "
						+ "and t.done <> 0");
		query.setParameter("owner", executor);
		return (Long)query.uniqueResult();
	}

	@Override
	public Long getArchiveCount(Human executor) {
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery("select count(*) from Task as t where t.owner = :owner and (t.archive = true) and "
						+ "(((t.owner.unit.owner = :manager or t.owner.unit.company.owner = :manager) and t.author <> t.owner) or t.author = :manager)");
		query.setParameter("owner", executor);
		return (Long)query.uniqueResult();
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
