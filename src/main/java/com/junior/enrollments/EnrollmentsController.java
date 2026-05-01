package com.junior.enrollments;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
