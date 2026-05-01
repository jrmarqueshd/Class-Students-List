package com.junior.courses.dto;

import java.time.LocalDateTime;

public record CourseResponse (
        Long id,
        String name,
        String code,
        String description,
        Integer workload_hours,
        Boolean active,
        LocalDateTime create_at,
        LocalDateTime update_at
) {
}
