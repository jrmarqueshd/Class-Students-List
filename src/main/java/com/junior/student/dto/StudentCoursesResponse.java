package com.junior.student.dto;

import com.junior.courses.dto.CourseResponse;

import java.util.List;

public record StudentCoursesResponse(
        Long student_id,
        String student_name,
        List<CourseResponse> courses
) {
}
