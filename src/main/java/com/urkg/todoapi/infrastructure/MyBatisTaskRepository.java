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
    public Optional<Task> findById(Integer id) {
        return taskMapper.findById(id);
    }

    @Override
    public Task insert(Task task) {
        taskMapper.insert(task);
        return task;
    }
}
