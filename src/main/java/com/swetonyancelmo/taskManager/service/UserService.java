package com.swetonyancelmo.taskManager.service;

import com.swetonyancelmo.taskManager.dtos.request.CreateUserRequestDTO;
import com.swetonyancelmo.taskManager.exceptions.EmailAlreadyRegisteredException;
import com.swetonyancelmo.taskManager.exceptions.ResourceNotFoundException;
import com.swetonyancelmo.taskManager.mapper.UserMapper;
import com.swetonyancelmo.taskManager.models.User;
import com.swetonyancelmo.taskManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> listAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com o ID " + id + " não encontrado."));
    }

    public User create(CreateUserRequestDTO dto) {
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new EmailAlreadyRegisteredException("O e-mail informado já está cadastrado.");
        }
        User newUser = userMapper.toEntity(dto);
        String passwordEncrypted = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(passwordEncrypted);

        return userRepository.save(newUser);
    }

    public User update(Long id, CreateUserRequestDTO dto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com o ID " + id + " não encontrado."));
        userMapper.updateEntityFromDto(dto, existingUser);
        return userRepository.save(existingUser);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
