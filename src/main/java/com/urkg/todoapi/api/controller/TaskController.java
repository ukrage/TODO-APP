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
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setContent(taskRequest.getContent());
        task = taskService.create(task);
        URI uri = uriComponentsBuilder.path("/tasks/{id}").buildAndExpand(task.getId()).toUri();
        return ResponseEntity.created(uri).body(task);
    }

    @PutMapping("/{taskId}")
    public Task update(@PathVariable Long taskId, @RequestBody TaskRequest taskRequest) {
        Task task = new Task();
        task.setId(taskId);
        task.setTitle(taskRequest.getTitle());
        task.setContent(taskRequest.getContent());
        return taskService.update(task);
    }

    @PatchMapping("/{taskId}")
    public Task patch(@PathVariable Long taskId, @RequestBody PatchRequest patchRequest) {
        Task task = new Task();
        task.setId(taskId);
        task.setFinishedFlg(patchRequest.isFinishedFlg());
        return taskService.patch(task);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> delete(@PathVariable Long taskId) {
        taskService.delete(taskId);
        return ResponseEntity.noContent().build();
    }
}
