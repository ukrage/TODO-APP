package com.urkg.todoapi.infrastructure;

import com.urkg.todoapi.domain.model.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TaskMapper {
    @Select("SELECT * FROM tasks")
    List<Task> findAll();

    @Select("SELECT * FROM tasks WHERE id = #{id}")
    Optional<Task> findById(Integer id);
}
