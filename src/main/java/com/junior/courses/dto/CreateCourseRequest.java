package com.junior.courses.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record CreateCourseRequest(

        @NotBlank(message = "O nome do curso não pode ser vazio")
        @Size(max = 120, message = "O nome do curso pode ter no máximo 150 caracteres")
        String name,

        @NotBlank(message = "O campo de código do curso não pode ser vazio")
        @Size(max = 30, message = "O campo de código do curso pode ter no máximo 30 caracteres")
        String code,

        @NotBlank(message = "O campo de descrição do curso não pode ser vazio")
        @Size(max = 10000, message = "O campo de descrição do curso pode ter no máximo 10000 caracteres")
        String description,

        @NotNull(message = "O campo de carga horário do curso não pode ser vazio")
        Integer workload_hours,

        @NotNull(message = "O campo de status do curso não pode ser vazio")
        Boolean active
) {
}