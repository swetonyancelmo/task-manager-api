package com.swetonyancelmo.taskManager.controller;

import com.swetonyancelmo.taskManager.dtos.CreateUserRequestDTO;
import com.swetonyancelmo.taskManager.dtos.UserResponseDTO;
import com.swetonyancelmo.taskManager.mapper.UserMapper;
import com.swetonyancelmo.taskManager.models.User;
import com.swetonyancelmo.taskManager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listAll() {
        List<UserResponseDTO> dtos = userService.listAll()
                .stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(userMapper.toResponseDTO(user));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody CreateUserRequestDTO dto) {
        User newUser = userService.create(dto);
        return new ResponseEntity<>(userMapper.toResponseDTO(newUser), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CreateUserRequestDTO dto) {
        User userUpdated = userService.update(id, dto);
        return ResponseEntity.ok(userMapper.toResponseDTO(userUpdated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
