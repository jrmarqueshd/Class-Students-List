package com.junior.enrollments;

import org.springframework.stereotype.Service;

import com.junior.shared.exception.ResourceNotFoundException;

import com.junior.enrollments.dto.EnrollmentStudentRequest;
import com.junior.enrollments.dto.EnrollmentStudentResponse;

import com.junior.courses.CourseEntity;
import com.junior.courses.CoursesRepository;

import com.junior.student.StudentEntity;
import com.junior.student.StudentsRepository;

@Service
public class EnrollmentService {
    private final EnrollmentsRepository enrollmentsRepository;

    private final StudentsRepository studentsRepository;
    private final CoursesRepository coursesRepository;

    public EnrollmentService (
            EnrollmentsRepository enrollmentsRepository,
            StudentsRepository studentsRepository,
            CoursesRepository coursesRepository
    ) {
        this.enrollmentsRepository = enrollmentsRepository;
        this.studentsRepository = studentsRepository;
        this.coursesRepository = coursesRepository;
    }

    public EnrollmentStudentResponse enrollmentStudent (EnrollmentStudentRequest request) {
        StudentEntity student = studentsRepository.findById(request.student_id())
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado"));

        CourseEntity course = coursesRepository.findById(request.course_id())
                .orElseThrow(() -> new ResourceNotFoundException("Curso não encontrado"));

        EnrollmentEntity enrollment = new EnrollmentEntity(
            student,
            course,
            request.status()
        );

        EnrollmentEntity savedEnrollment = enrollmentsRepository.save(enrollment);

        return new EnrollmentStudentResponse(savedEnrollment.getEnrollmentId(), "Aluno cadastrado ao curso com sucesso");
    }
}
