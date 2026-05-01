package com.junior.enrollments;

import com.junior.courses.dto.CourseStudentsResponse;
import com.junior.enrollments.dto.DeleteEnrollmentResponse;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.junior.enrollments.dto.EnrollmentStudentRequest;
import com.junior.enrollments.dto.EnrollmentStudentResponse;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentsController {
    private final EnrollmentService enrollmentService;

    public EnrollmentsController (EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public EnrollmentStudentResponse registerStudent (@RequestBody @Valid EnrollmentStudentRequest request) {
        return enrollmentService.enrollmentStudent(request);
    }

    @DeleteMapping("/student/{student_id}/course/{course_id}/cancel")
    public DeleteEnrollmentResponse cancelStudentEnrollment (@PathVariable Long student_id, @PathVariable Long course_id) {
        return enrollmentService.cancelStudentEnrollment(student_id, course_id);
    }

    @DeleteMapping("/student/{student_id}/cancel")
    public DeleteEnrollmentResponse cancelAllEnrollmentByStudentId (@PathVariable Long student_id) {
        return enrollmentService.cancelAllEnrollmentByStudentId(student_id);
    }

    @DeleteMapping("/course/{course_id}/cancel")
    public DeleteEnrollmentResponse cancelAllEnrollmentByCourseId (@PathVariable Long course_id) {
        return enrollmentService.cancelAllEnrollmentByCourseId(course_id);
    }
}
