package com.cssru.cncompanies.service;

import java.util.List;

import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.domain.Login;
import com.cssru.cncompanies.domain.Task;
import com.cssru.cncompanies.exception.AccessDeniedException;
import com.cssru.cncompanies.proxy.TaskJsonProxy;
import com.cssru.cncompanies.synch.SynchContainer;

public interface TaskService {
	void addTask(Task task, Human executor, Login managerLogin);
	List<Task> listUndoneTask(Human executor, Login managerLogin);
	List<Task> listDoneTask(Human executor, Login managerLogin);
	List<Task> listAllTask(Human executor, Login managerLogin);
	List<Task> listAllTask(Login managerLogin);
	List<Task> listTaskWithAuthor(Human author, Login managerLogin);
	List<Task> listUndoneTasksForSlave(Login managerLogin);
	List<Task> listDoneTasksForSlave(Login managerLogin);
	void removeTask(Long id, Login managerLogin);
	Task getTask(Long id, Login managerLogin);
	void updateTask(Task task, Login managerLogin);
	// archive
	List<Task> listArchiveTasksWithAuthor(Human author, Login managerLogin);
	List<Task> listArchiveTasksForSlave(Login managerLogin);
	List<Task> listArchiveTasks(Human executor, Login managerLogin);
	// statistic
	Long getNormalTaskCount(Human executor, Login managerLogin);
	Long getExpiredTaskCount(Human executor, Login managerLogin);
	Long getNearestTaskCount(Human executor, Login managerLogin);
	Long getDoneTaskCount(Human executor, Login managerLogin);
	Long getArchiveTaskCount(Human executor, Login managerLogin);
	// for ajax requests
	void setTaskContent(Task task, String content, Login managerLogin);
	void setTaskComment(Task task, String comment, Login managerLogin);
	//RESTful
	SynchContainer<TaskJsonProxy> synchronize(SynchContainer<TaskJsonProxy> request, Login managerLogin) throws AccessDeniedException;

}
