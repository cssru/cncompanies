package com.cssru.companies.service.impl;

import com.cssru.companies.dao.TaskDao;
import com.cssru.companies.domain.Employee;
import com.cssru.companies.domain.Login;
import com.cssru.companies.domain.Task;
import com.cssru.companies.proxy.TaskJsonProxy;
import com.cssru.companies.service.AccountService;
import com.cssru.companies.service.EmployeeService;
import com.cssru.companies.service.TaskService;
import com.cssru.companies.synch.ItemDescriptor;
import com.cssru.companies.synch.SynchContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TaskDao taskDao;

    @Transactional
    @Override
    public void addTask(Task task, Employee executor, Login managerLogin) {
        taskDao.addTask(task, executor, managerLogin);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Task> listUndoneTask(Employee executor, Login managerLogin) {
        return taskDao.listUndoneTask(executor, managerLogin);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Task> listUndoneTasksForSlave(Login managerLogin) {
        return taskDao.listUndoneTasksForSlave(managerLogin);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Task> listDoneTasksForSlave(Login managerLogin) {
        return taskDao.listDoneTasksForSlave(managerLogin);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Task> listDoneTask(Employee executor, Login managerLogin) {
        return taskDao.listDoneTask(executor, managerLogin);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Task> listAllTask(Employee executor, Login managerLogin) {
        return taskDao.listAllTask(executor, managerLogin);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Task> listAllTask(Login managerLogin) {
        return taskDao.listAllTask(managerLogin);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Task> listTaskWithAuthor(Employee author, Login managerLogin) {
        return taskDao.listTaskWithAuthor(author, managerLogin);
    }

    @Transactional
    @Override
    public void removeTask(Long id, Login managerLogin) {
        taskDao.removeTask(id, managerLogin);
    }

    @Transactional(readOnly = true)
    @Override
    public Task getTask(Long id, Login managerLogin) {
        return taskDao.getTask(id, managerLogin);
    }

    @Transactional
    @Override
    public void updateTask(Task task, Login managerLogin) {
        taskDao.updateTask(task, managerLogin);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Task> listArchiveTasksWithAuthor(Employee author,
                                                 Login managerLogin) {
        return taskDao.listArchiveTasksWithAuthor(author, managerLogin);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Task> listArchiveTasksForSlave(Login managerLogin) {
        return taskDao.listArchiveTasksForSlave(managerLogin);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Task> listArchiveTasks(Employee executor, Login managerLogin) {
        return taskDao.listArchiveTasks(executor, managerLogin);
    }

    // statistic
    @Transactional(readOnly = true)
    @Override
    public Long getNormalTaskCount(Employee executor, Login managerLogin) {
        return taskDao.getNormalTaskCount(executor, managerLogin);
    }

    @Transactional(readOnly = true)
    @Override
    public Long getExpiredTaskCount(Employee executor, Login managerLogin) {
        return taskDao.getExpiredTaskCount(executor, managerLogin);
    }

    @Transactional(readOnly = true)
    @Override
    public Long getNearestTaskCount(Employee executor, Login managerLogin) {
        return taskDao.getNearestTaskCount(executor, managerLogin);
    }

    @Transactional(readOnly = true)
    @Override
    public Long getDoneTaskCount(Employee executor, Login managerLogin) {
        return taskDao.getDoneTaskCount(executor, managerLogin);
    }

    @Transactional(readOnly = true)
    @Override
    public Long getArchiveTaskCount(Employee executor, Login managerLogin) {
        return taskDao.getArchiveTaskCount(executor, managerLogin);
    }

    // for ajax requests
    @Transactional
    @Override
    public void setTaskContent(Task task, String content, Login managerLogin) {
        taskDao.setTaskContent(task, content, managerLogin);
    }

    @Transactional
    @Override
    public void setTaskComment(Task task, String comment, Login managerLogin) {
        taskDao.setTaskComment(task, comment, managerLogin);
    }

    // RESTful
    // Synchronization
    @Transactional
    @Override
    public SynchContainer<TaskJsonProxy> synchronize(SynchContainer<TaskJsonProxy> request, Login managerLogin) throws AccessDeniedException {
        SynchContainer<TaskJsonProxy> response = new SynchContainer<TaskJsonProxy>();
        //TODO do synch!!!

        // delete from database tasks, which are deleted on client's device
        for (ItemDescriptor nextItem : request.getDeletedItems()) {
            if (nextItem.getServerId() > 0)
                removeTask(nextItem.getServerId(), managerLogin);
        }

        // items contains all the tasks descriptors on client's device
        for (ItemDescriptor nextItem : request.getItems()) {
            if (nextItem.getServerId() > 0) {
                // if task present on client's device, but absent on server,
                // then it is deleted on server -> must delete on client
                if (getTask(nextItem.getServerId(), managerLogin) == null) {
                    response.addDeletedItem(nextItem);
                }
            }
        }

        // checking Server's objects, send new objects to client
        List<Task> serverNewTasks = taskDao.listTask(request.getLastSynchTime(), managerLogin);
        for (Task nextTask : serverNewTasks) {
            response.addObject(new TaskJsonProxy(nextTask));
        }

        // check all task objects, sent from Client
        for (TaskJsonProxy nextTask : request.getObjects()) {
            if (nextTask.getServerId() > 0) {
                // task already exists in server database
                Task serverTask = getTask(nextTask.getServerId(), managerLogin);
                if (serverTask != null) {
                    if (serverTask.getLastModified().getTime() > nextTask.getLastModified()) {
                        // server's task's version is more actual, than client's task's version
                        // send server's version to client
                        serverTask.setClientId(nextTask.getClientId());
                        if (!response.getObjects().contains(serverTask))
                            response.addObject(new TaskJsonProxy(serverTask));
                    } else {
                        // client's task's version more actual
                        // store client's data into database
                        if (!serverTask.isReadonly()) {
                            copyFromJsonProxy(nextTask, serverTask, managerLogin);
                            updateTask(serverTask, managerLogin);
                        } else {
                            // in readonly task client can change only "done" field
                            serverTask.setDone(new Date(nextTask.getDone()));
                            updateTask(serverTask, managerLogin);
                        }
                    }
                }
            } else {
                // this is new task for server
                long clientId = nextTask.getClientId();
                long serverId;
                Task newTask = new Task();
                copyFromJsonProxy(nextTask, newTask, managerLogin);
                Employee executor;
                if (nextTask.getOwnerId() > 0) {
                    // find executor in database if defined
                    executor = employeeService.getHuman(nextTask.getOwnerId(), managerLogin);
                } else {
                    // if executor id not defined, then executor is logged user (storing user's own task)
                    executor = managerLogin.getHuman();
                }
                if (executor != null) {
                    addTask(newTask, executor, managerLogin);
                    serverId = newTask.getServerId();
                    // response items contains items, added to database with newly assigned server Id
                    response.addItem(new ItemDescriptor(clientId, serverId));
                }
            }

        }

        // set last synchronization time in response and in manager's (user's) login
        long lastSynchTime = System.currentTimeMillis();
        response.setLastSynchTime(lastSynchTime);
        managerLogin.setLastSynch(new Date(lastSynchTime));
        accountService.updateLogin(managerLogin, false);
        return response;
    }

    private void copyFromJsonProxy(TaskJsonProxy from, Task to, Login managerLogin) throws AccessDeniedException {
        Employee owner = employeeService.getHuman(from.getOwnerId(), managerLogin);
        if (owner == null) owner = managerLogin.getHuman();
        to.setOwner(owner);

        Employee author = employeeService.getHuman(from.getAuthorId(), managerLogin);
        if (author == null) author = managerLogin.getHuman();
        to.setAuthor(author);
        to.setCreated(new Date(from.getCreated()));
        to.setBegin(new Date(from.getBegin()));
        to.setExpires(new Date(from.getExpires()));
        to.setDone(new Date(from.getDone()));
        to.setContent(from.getContent());
        to.setComment(from.getComment());
        to.setDifficulty(from.getDifficulty());
        to.setParentTaskId(from.getParentTaskId());
        to.setAlertTime(from.getAlertTime());
        to.setArchive(from.isArchive());
        to.setProjectId(from.getProjectId());
        to.setMetadata(from.getMetadata());
    }
}
