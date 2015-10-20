package com.cssru.companies.dao;

import com.cssru.companies.domain.Post;
import com.cssru.companies.domain.Task;

import java.util.List;

public interface TaskDao {
    void create(Task task);

    Task get(Long id);

    List<Task> listDone(Post executor);

    List<Task> listUndone(Post executor);

    List<Task> listAll(Post executor);

    List<Task> listArchive(Post executor);
    void update(Task task);
    void delete(Long id);
}
