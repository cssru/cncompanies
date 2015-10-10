package com.cssru.cncompanies.service;

import com.cssru.cncompanies.domain.Employee;
import com.cssru.cncompanies.domain.Login;
import com.cssru.cncompanies.domain.Task;
import com.cssru.cncompanies.proxy.TaskJsonProxy;
import com.cssru.cncompanies.synch.SynchContainer;

import java.util.List;

public interface TaskService {
    void addTask(Task task, Employee executor, Login managerLogin);

    List<Task> listUndoneTask(Employee executor, Login managerLogin);

    List<Task> listDoneTask(Employee executor, Login managerLogin);

    List<Task> listAllTask(Employee executor, Login managerLogin);

    List<Task> listAllTask(Login managerLogin);

    List<Task> listTaskWithAuthor(Employee author, Login managerLogin);

    List<Task> listUndoneTasksForSlave(Login managerLogin);

    List<Task> listDoneTasksForSlave(Login managerLogin);

    void removeTask(Long id, Login managerLogin);

    Task getTask(Long id, Login managerLogin);

    void updateTask(Task task, Login managerLogin);

    // archive
    List<Task> listArchiveTasksWithAuthor(Employee author, Login managerLogin);

    List<Task> listArchiveTasksForSlave(Login managerLogin);

    List<Task> listArchiveTasks(Employee executor, Login managerLogin);

    // statistic
    Long getNormalTaskCount(Employee executor, Login managerLogin);

    Long getExpiredTaskCount(Employee executor, Login managerLogin);

    Long getNearestTaskCount(Employee executor, Login managerLogin);

    Long getDoneTaskCount(Employee executor, Login managerLogin);

    Long getArchiveTaskCount(Employee executor, Login managerLogin);

    // for ajax requests
    void setTaskContent(Task task, String content, Login managerLogin);

    void setTaskComment(Task task, String comment, Login managerLogin);

    //RESTful
    SynchContainer<TaskJsonProxy> synchronize(SynchContainer<TaskJsonProxy> request, Login managerLogin) throws AccessDeniedException;

}
