package com.urkg.todoapi.api.controller;

import com.urkg.todoapi.api.controller.request.CreateRequest;
import com.urkg.todoapi.api.service.TaskService;
import com.urkg.todoapi.domain.model.Task;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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

    @PostMapping
    public ResponseEntity<Task> create(
            @RequestBody @Valid CreateRequest createRequest,
            UriComponentsBuilder uriComponentsBuilder) {
        Task task = taskService.insert(createRequest);
        URI uri = uriComponentsBuilder.path("/tasks/{id}").buildAndExpand(task.getId()).toUri();
        return ResponseEntity.created(uri).body(task);
    }
}
