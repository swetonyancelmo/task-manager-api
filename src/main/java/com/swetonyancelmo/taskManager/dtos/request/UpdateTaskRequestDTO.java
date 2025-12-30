package com.swetonyancelmo.taskManager.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTaskRequestDTO {

    private String title;
    private String description;
    private Boolean completed;
}
