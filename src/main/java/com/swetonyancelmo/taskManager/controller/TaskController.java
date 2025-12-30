package com.swetonyancelmo.taskManager.controller;

import com.swetonyancelmo.taskManager.dtos.request.CreateTaskRequestDTO;
import com.swetonyancelmo.taskManager.dtos.response.TaskResponseDTO;
import com.swetonyancelmo.taskManager.dtos.request.UpdateTaskRequestDTO;
import com.swetonyancelmo.taskManager.mapper.TaskMapper;
import com.swetonyancelmo.taskManager.models.Task;
import com.swetonyancelmo.taskManager.models.User;
import com.swetonyancelmo.taskManager.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Fluxo de Tarefas", description = "Endpoints de CRUD para Tarefas")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskMapper taskMapper;

    @GetMapping
    @Operation(summary = "Lista todas as Tarefas", description = "Faz a listagem de todas as tarefas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefas encontradas com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    public ResponseEntity<List<TaskResponseDTO>> listAll() {
        List<Task> tasks = taskService.listAll();
        List<TaskResponseDTO> dtos = tasks.stream()
                .map(taskMapper::convertToResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna tarefa por ID", description = "Retorna uma tarefa pelo ID informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa encontrada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    public ResponseEntity<TaskResponseDTO> findById(@PathVariable Long id) {
        Task taskFound = taskService.findById(id);
        return ResponseEntity.ok(taskMapper.convertToResponseDTO(taskFound));
    }

    @PostMapping
    @Operation(summary = "Cria tarefa", description = "Faz a criação de uma nova tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao criar a tarefa"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    public ResponseEntity<TaskResponseDTO> create(@RequestBody @Valid CreateTaskRequestDTO dto,
                                                  @AuthenticationPrincipal User userLogged) {
        Task newTask = taskService.create(dto, userLogged);
        return new ResponseEntity<>(taskMapper.convertToResponseDTO(newTask), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma tarefa", description = "Atualiza uma tarefa por completo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar a tarefa"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    public ResponseEntity<TaskResponseDTO> update(@RequestBody @Valid UpdateTaskRequestDTO dto, @PathVariable Long id) {
        Task updatedTask = taskService.update(id, dto);
        return ResponseEntity.ok(taskMapper.convertToResponseDTO(updatedTask));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Conclui uma tarefa", description = "Conclui uma tarefa pelo ID informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa concluída"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    public ResponseEntity<TaskResponseDTO> conclude(@PathVariable Long id) {
        Task concludeTask = taskService.concludeTask(id);
        return ResponseEntity.ok(taskMapper.convertToResponseDTO(concludeTask));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma tarefa", description = "Deleta uma tarefa pelo ID informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tarefa deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
