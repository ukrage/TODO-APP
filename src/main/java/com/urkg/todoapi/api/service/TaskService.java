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
        Optional<Task> task = taskDomainService.findById(id);
        if (task.isPresent()) {
            return task.get();
        } else {
            throw new TaskNotFoundException("Task not found");
        }
    }

    public Task create(Task task) {
        return taskDomainService.create(task);
    }

    public Task update(Task task) {
        Optional<Task> optionalTask = taskDomainService.findById(task.getId());
        if (optionalTask.isPresent()) {
            return taskDomainService.update(task);
        } else {
            throw new TaskNotFoundException("Task not found");
        }
    }

    public Task patch(Task task) {
        Optional<Task> optionalTask = taskDomainService.findById(task.getId());
        if (optionalTask.isPresent()) {
            task.setTitle(optionalTask.get().getTitle());
            task.setContent(optionalTask.get().getContent());
            return taskDomainService.patch(task);
        } else {
            throw new TaskNotFoundException("Task not found");
        }
    }

    public void delete(Long id) {
        Optional<Task> task = taskDomainService.findById(id);
        if (task.isPresent()) {
            taskDomainService.delete(id);
        } else {
            throw new TaskNotFoundException("Task not found");
        }
    }
}
