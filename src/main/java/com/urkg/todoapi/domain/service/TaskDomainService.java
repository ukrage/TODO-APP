package com.urkg.todoapi.domain.service;

import com.urkg.todoapi.api.controller.request.CreateRequest;
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

    public Optional<Task> findById(Integer id) {
        return taskRepository.findById(id);
    }

    public Task insert(CreateRequest createRequest) {
        Task task = new Task();
        task.setTitle(createRequest.getTitle());
        task.setContent(createRequest.getContent());
        task.setFinishedFlg(false);
        return taskRepository.insert(task);
    }
}
