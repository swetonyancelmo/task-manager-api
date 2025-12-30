package com.swetonyancelmo.taskManager.mapper;

import com.swetonyancelmo.taskManager.dtos.request.CreateUserRequestDTO;
import com.swetonyancelmo.taskManager.dtos.response.UserResponseDTO;
import com.swetonyancelmo.taskManager.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    public User toEntity(CreateUserRequestDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }

    public void updateEntityFromDto(CreateUserRequestDTO dto, User existingUser) {
        if (dto.getUsername() != null && !dto.getUsername().isBlank()) {
            existingUser.setUsername(dto.getUsername());
        }
        if(dto.getEmail() != null && !dto.getEmail().isBlank()) {
            existingUser.setEmail(dto.getEmail());
        }
        if(dto.getPassword() != null && !dto.getPassword().isBlank()) {
            existingUser.setPassword(dto.getPassword());
        }
    }
}
