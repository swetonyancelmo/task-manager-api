package com.swetonyancelmo.taskManager.controller;

import com.swetonyancelmo.taskManager.dtos.request.CreateUserRequestDTO;
import com.swetonyancelmo.taskManager.dtos.response.UserResponseDTO;
import com.swetonyancelmo.taskManager.mapper.UserMapper;
import com.swetonyancelmo.taskManager.models.User;
import com.swetonyancelmo.taskManager.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Tag(name = "Fluxo de Usuários", description = "Endpoints de CRUD para Usuários")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    @Operation(summary = "Retorna usuários", description = "Retorna todos os usuários cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários encontrados"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    public ResponseEntity<List<UserResponseDTO>> listAll() {
        List<UserResponseDTO> dtos = userService.listAll()
                .stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna usuário por ID", description = "Retorna todos os usuários pelo ID informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(userMapper.toResponseDTO(user));
    }

    @PostMapping
    @Operation(summary = "Cadastra um usuário", description = "Faz o cadastro de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado"),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar o usuário"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody CreateUserRequestDTO dto) {
        User newUser = userService.create(dto);
        return new ResponseEntity<>(userMapper.toResponseDTO(newUser), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Cadastra um usuário", description = "Faz o cadastro de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar o usuário"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CreateUserRequestDTO dto) {
        User userUpdated = userService.update(id, dto);
        return ResponseEntity.ok(userMapper.toResponseDTO(userUpdated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cadastra um usuário", description = "Faz o cadastro de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
