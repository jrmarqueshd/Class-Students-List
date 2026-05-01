package com.junior.enrollments.dto;

import jakarta.validation.constraints.NotNull;

public record EnrollmentStudentRequest(
        @NotNull(message = "O id do aluno é obrigatório")
        Long student_id,

        @NotNull(message = "O id do curso é obrigatório")
        Long course_id,

        @NotNull(message = "O campo de status é obrigatório")
        Boolean status
) {
}
