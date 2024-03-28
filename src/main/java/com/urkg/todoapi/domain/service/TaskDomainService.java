package com.urkg.todoapi.domain.service;

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

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    public Task create(Task task) {
        task.setFinishedFlg(false);
        return taskRepository.insert(task);
    }

    public Task update(Task task) {
        return taskRepository.update(task);
    }

    public Task patch(Task task) {
        return taskRepository.patch(task);
    }

    public void delete(Long id) {
        taskRepository.delete(id);
    }
}
