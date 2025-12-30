package com.swetonyancelmo.taskManager.controller;

import com.swetonyancelmo.taskManager.dtos.request.AuthenticationDataRequestDTO;
import com.swetonyancelmo.taskManager.dtos.response.DataTokenResponseDTO;
import com.swetonyancelmo.taskManager.models.User;
import com.swetonyancelmo.taskManager.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name = "Login", description = "Realiza o Login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Operation(summary = "Login", description = "Faz o login do usuário cadastrado")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDataRequestDTO dto) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
        var authentication = manager.authenticate(authenticationToken);
        var tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new DataTokenResponseDTO(tokenJWT));
    }
}
