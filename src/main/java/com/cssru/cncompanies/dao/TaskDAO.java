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

	List<Task> listDone(Human executor);
	List<Task> listUndone(Human executor);
	List<Task> listAll(Human executor);

	List<Task> listDoneForSlave(Human manager);
	List<Task> listUndoneForSlave(Human manager);

	List<Task> listDoneVisible(Human manager);// all done tasks visible for manager
	List<Task> listUndoneVisible(Human manager);// all undone tasks visible for manager
	List<Task> listDoneWithAuthor(Human author);
	List<Task> listUndoneWithAuthor(Human author);

	// archive
	List<Task> listArchive(Human executor);
	List<Task> listArchiveWithAuthor(Human author);
	List<Task> listArchiveForSlave(Human manager);

	// statistic
	Long getNormalCount(Human executor);
	Long getExpiredCount(Human executor);
	Long getNearestCount(Human executor);
	Long getDoneCount(Human executor);
	Long getArchiveCount(Human executor);
	// for ajax requests
	void setTaskContent(Long id, String content);
	void setTaskComment(Long id, String comment);
}
