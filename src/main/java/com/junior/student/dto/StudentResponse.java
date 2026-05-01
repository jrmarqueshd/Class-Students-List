package com.junior.student.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record StudentResponse(
        Long id,
        String name,
        String email,
        LocalDate birth_date,
        LocalDateTime create_at
) {
}
