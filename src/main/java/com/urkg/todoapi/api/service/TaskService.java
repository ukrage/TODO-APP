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

    public Task findById(Integer id) {
        Optional<Task> task = taskDomainService.findById(id);
        if (task.isPresent()) {
            return task.get();
        } else {
            throw new TaskNotFoundException("Task not found");
        }
    }
}
