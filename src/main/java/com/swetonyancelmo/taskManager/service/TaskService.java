package com.swetonyancelmo.taskManager.service;

import com.swetonyancelmo.taskManager.dtos.request.CreateTaskRequestDTO;
import com.swetonyancelmo.taskManager.dtos.request.UpdateTaskRequestDTO;
import com.swetonyancelmo.taskManager.exceptions.ResourceNotFoundException;
import com.swetonyancelmo.taskManager.models.Task;
import com.swetonyancelmo.taskManager.models.User;
import com.swetonyancelmo.taskManager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> listAll() {
        return taskRepository.findAll();
    }

    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa com ID " + id + " não encontrada."));
    }

    public Task create(CreateTaskRequestDTO dto, User userLoggedIn){
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCompleted(false);
        task.setUser(userLoggedIn);
        return taskRepository.save(task);
    }

    public Task update(Long id, UpdateTaskRequestDTO dto) {
        Task taskExistings = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa com ID " + id + " não encontrada."));

        if(!dto.getTitle().isEmpty()) taskExistings.setTitle(dto.getTitle());
        if(!dto.getDescription().isEmpty()) taskExistings.setDescription(dto.getDescription());
        if(dto.getCompleted() != null) taskExistings.setCompleted(dto.getCompleted());

        return taskRepository.save(taskExistings);
    }

    public Task concludeTask(Long id) {
        Task taskExistings = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa com ID " + id + " não encontrada."));
        taskExistings.setCompleted(true);

        return taskRepository.save(taskExistings);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
