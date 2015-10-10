package com.cssru.cncompanies.dao;

import com.cssru.cncompanies.domain.Employee;
import com.cssru.cncompanies.domain.Task;

import java.util.List;

public interface TaskDao {
    void add(Task task);

    void update(Task task);

    void delete(Long id);

    Task get(Long id);

    List<Task> list(long lastModified);

    List<Task> listDone(Employee executor);

    List<Task> listUndone(Employee executor);

    List<Task> listAll(Employee executor);

    List<Task> listDoneForSlave(Employee manager);

    List<Task> listUndoneForSlave(Employee manager);

    List<Task> listDoneVisible(Employee manager);// all done tasks visible for manager

    List<Task> listUndoneVisible(Employee manager);// all undone tasks visible for manager

    List<Task> listDoneWithAuthor(Employee author);

    List<Task> listUndoneWithAuthor(Employee author);

    // archive
    List<Task> listArchive(Employee executor);

    List<Task> listArchiveWithAuthor(Employee author);

    List<Task> listArchiveForSlave(Employee manager);

    // statistic
    Long getNormalCount(Employee executor);

    Long getExpiredCount(Employee executor);

    Long getNearestCount(Employee executor);

    Long getDoneCount(Employee executor);

    Long getArchiveCount(Employee executor);

    // for ajax requests
    void setTaskContent(Long id, String content);

    void setTaskComment(Long id, String comment);
}
