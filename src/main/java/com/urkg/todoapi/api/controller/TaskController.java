package com.urkg.todoapi.api.controller;

import com.urkg.todoapi.api.controller.request.PatchRequest;
import com.urkg.todoapi.api.controller.request.TaskRequest;
import com.urkg.todoapi.api.service.TaskService;
import com.urkg.todoapi.domain.model.Task;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public Task findById(@PathVariable Long taskId) {
        return taskService.findById(taskId);
    }

    @PostMapping
    public ResponseEntity<Task> create(
            @RequestBody @Valid TaskRequest taskRequest,
            UriComponentsBuilder uriComponentsBuilder) {
        Task task = taskService.insert(taskRequest);
        URI uri = uriComponentsBuilder.path("/tasks/{id}").buildAndExpand(task.getId()).toUri();
        return ResponseEntity.created(uri).body(task);
    }

    @PutMapping("/{taskId}")
    public Task update(@PathVariable Long taskId, @RequestBody TaskRequest taskRequest) {
        return taskService.update(taskId, taskRequest);
    }

    @PatchMapping("/{taskId}")
    public Task patch(@PathVariable Long taskId, @RequestBody PatchRequest patchRequest) {
        return taskService.patch(taskId, patchRequest.isFinishedFlg());
    }
}
