package com.urkg.todoapi.api.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PatchRequest {
    @NotNull
    private boolean finishedFlg;
}
