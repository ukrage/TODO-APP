package com.urkg.todoapi.api.controller;

import com.urkg.todoapi.api.service.TaskService;
import com.urkg.todoapi.domain.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public List<Task> findAll() {
        return taskService.findAll();
    }

    @GetMapping("/{taskId}")
    public Task findById(@PathVariable Integer taskId) {
        return taskService.findById(taskId);
    }
}
