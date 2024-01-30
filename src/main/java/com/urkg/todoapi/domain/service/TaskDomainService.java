package com.urkg.todoapi.domain.service;

import com.urkg.todoapi.api.controller.request.TaskRequest;
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

    public Task insert(TaskRequest taskRequest) {
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setContent(taskRequest.getContent());
        task.setFinishedFlg(false);
        return taskRepository.insert(task);
    }

    public Task update(Task task, TaskRequest taskRequest) {
        task.setTitle(taskRequest.getTitle());
        task.setContent(taskRequest.getContent());
        return taskRepository.update(task);
    }

    public Task patch(Task task, boolean finishedFlg) {
        task.setFinishedFlg(finishedFlg);
        return taskRepository.patch(task);
    }
}
