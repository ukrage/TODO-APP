package com.urkg.todoapi.api.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TaskRequest {
    @NotNull
    @Length(min = 1, max = 50, message = "titleは1文字以上５０文字以下してください。")
    private String title;

    @Length(min = 0, max = 255, message = "contentは0文字以上２５５文字以下してください。")
    private String content;
}
