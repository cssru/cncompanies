package com.cssru.cncompanies.dao;

import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.domain.Login;
import com.cssru.cncompanies.domain.Task;

import java.util.List;

public interface TaskDAO {
	void add(Task task);
	void update(Task task);
	void delete(Long id);
	Task get(Long id);
	List<Task> list(long lastModified);

	List<Task> listDone(Human performer);
	List<Task> listUndone(Human performer);

	List<Task> listDoneForSlave(Human manager);
	List<Task> listUndoneForSlave(Human manager);

	List<Task> listForExecutor(Human performer);
	List<Task> listVisible(Human manager);// all tasks visible for manager
	List<Task> listWithAuthor(Human author);

	// archive
	List<Task> listArchiveWithAuthor(Human author);
	List<Task> listArchiveForSlave(Human manager);

	// statistic
	Long getNormalCount(Human performer);
	Long getExpiredCount(Human performer);
	Long getNearestCount(Human performer);
	Long getDoneCount(Human performer);
	Long getArchiveCount(Human performer);
	// for ajax requests
	void setTaskContent(Long id, String content);
	void setTaskComment(Long id, String comment);
}
