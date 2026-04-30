package com.junior.student.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateStudentRequest (
        @NotBlank(message = "Nome é obrigatório")
        String name,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "E-mail inválido")
        String email,

        @NotNull(message = "Data de nascimento é obrigatória")
        LocalDate birth_date
) {
}
