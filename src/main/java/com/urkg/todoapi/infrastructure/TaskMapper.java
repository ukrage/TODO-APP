package com.urkg.todoapi.infrastructure;

import com.urkg.todoapi.domain.model.Task;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TaskMapper {
    @Select("SELECT * FROM tasks")
    List<Task> findAll();

    @Select("SELECT * FROM tasks WHERE id = #{id}")
    Optional<Task> findById(Long id);

    @Insert("INSERT INTO tasks (title, content, finishedFlg) VALUES (#{title},#{content},#{finishedFlg})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Task task);

    @Update("UPDATE tasks SET title = #{title}, content = #{content} WHERE id = #{id}")
    void update(Task task);

    @Update("UPDATE tasks SET finishedFlg = #{finishedFlg} WHERE id = #{id}")
    void patch(Task task);
}
