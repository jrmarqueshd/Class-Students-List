package com.junior.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateStudentRequest (
        @NotNull
        String name,

        @NotNull
        String email,

        @NotNull
        LocalDate birth_date
) {
}
