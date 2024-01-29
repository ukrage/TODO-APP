package com.urkg.todoapi.api.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateRequest {
    @NotNull
    @Size(min = 1, max = 50, message = "titleは1文字以上２５５文字以下してください。")
    private String title;

    @Size(min = 0, max = 255, message = "contentは0文字以上２５５文字以下してください。")
    private String content;
}
