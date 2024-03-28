package com.urkg.todoapi.domain.service;

import com.urkg.todoapi.api.exception.TaskNotFoundException;
import com.urkg.todoapi.domain.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskDomainService {
    private final TaskRepository taskRepository;

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task findById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            return task.get();
        } else {
            throw new TaskNotFoundException("Task not found");
        }
    }

    public Task create(Task task) {
        task.setFinishedFlg(false);
        
        taskRepository.insert(task);

        return task;
    }

    public Task update(Task task) {
        Task updatedTask = findById(task.getId());
        updatedTask.setTitle(task.getTitle());
        updatedTask.setContent(task.getContent());

        taskRepository.update(updatedTask);

        return updatedTask;
    }

    public Task patch(Task task) {
        return taskRepository.patch(task);
    }

    public void delete(Long id) {
        taskRepository.delete(id);
    }
}
