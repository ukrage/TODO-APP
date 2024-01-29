package com.urkg.todoapi.domain.model;

import lombok.Data;

@Data
public class Task {
    private Long id;
    private String title;
    private String content;
    private boolean finished;
}
