package com.urkg.todoapi.domain.service;

import com.urkg.todoapi.domain.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    List<Task> findAll();

    Optional<Task> findById(Long id);

    Task insert(Task task);

    Task update(Task task);

    Task patch(Task task);

    void delete(Long id);
}
