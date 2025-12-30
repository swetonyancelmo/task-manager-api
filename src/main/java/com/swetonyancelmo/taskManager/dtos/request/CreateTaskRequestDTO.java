package com.swetonyancelmo.taskManager.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTaskRequestDTO {

    @NotBlank(message = "O título não pode estar em branco.")
    @Size(min = 3, max = 100, message = "O título não pode ter mais que 100 caracteres.")
    private String title;

    @Size(max = 255, message = "A descrição não pode ter mais que 255 caracteres.")
    private String description;
}
