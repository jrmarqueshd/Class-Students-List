package com.junior.courses.dto;

import com.junior.student.dto.StudentResponse;

import java.util.List;

public record CourseStudentsResponse(
        Long course_id,
        String course_name,
        List<StudentResponse> students
) {
}
