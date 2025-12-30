package com.swetonyancelmo.taskManager.mapper;

import com.swetonyancelmo.taskManager.dtos.response.TaskResponseDTO;
import com.swetonyancelmo.taskManager.models.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskResponseDTO convertToResponseDTO(Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getCompleted()
        );
    }
}
