package com.urkg.todoapi.infrastructure;

import com.urkg.todoapi.domain.model.Task;
import com.urkg.todoapi.domain.service.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MyBatisTaskRepository implements TaskRepository {
    private final TaskMapper taskMapper;

    @Override
    public List<Task> findAll() {
        return taskMapper.findAll();
    }

    @Override
    public Optional<Task> findById(Long id) {
        return taskMapper.findById(id);
    }

    @Override
    public void insert(Task task) {
        taskMapper.insert(task);
    }

    @Override
    public void update(Task task) {
        taskMapper.update(task);
    }

    @Override
    public void patch(Task task) {
        taskMapper.patch(task);
    }

    @Override
    public void delete(Long id) {
        taskMapper.delete(id);
    }
}
