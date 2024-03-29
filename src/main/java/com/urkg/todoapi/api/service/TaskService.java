package com.urkg.todoapi.api.service;

import com.urkg.todoapi.api.exception.TaskNotFoundException;
import com.urkg.todoapi.domain.model.Task;
import com.urkg.todoapi.domain.service.TaskDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {
    private final TaskDomainService taskDomainService;

    public List<Task> findAll() {
        return taskDomainService.findAll();
    }

    public Task findById(Long id) {
        return taskDomainService.findById(id);
    }

    public Task create(Task task) {
        return taskDomainService.create(task);
    }

    public Task update(Task task) {
        return taskDomainService.update(task);
    }

    public Task patch(Task task) {
        return taskDomainService.patch(task);
    }

    public void delete(Long id) {
        Task task = taskDomainService.findById(id);

        taskDomainService.delete(id);
    }
}
