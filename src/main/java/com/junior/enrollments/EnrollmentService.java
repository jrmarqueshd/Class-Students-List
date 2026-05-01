package com.junior.enrollments;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.junior.shared.exception.ResourceNotFoundException;

import com.junior.enrollments.dto.DeleteEnrollmentResponse;
import com.junior.enrollments.dto.EnrollmentStudentRequest;
import com.junior.enrollments.dto.EnrollmentStudentResponse;

import com.junior.courses.CourseEntity;
import com.junior.courses.CoursesRepository;

import com.junior.student.StudentEntity;
import com.junior.student.StudentsRepository;

import java.util.List;

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

    public DeleteEnrollmentResponse cancelStudentEnrollment (Long student_id, Long course_id) {
        EnrollmentEntity enrollment = enrollmentsRepository.findByStudentIdAndCourseId(student_id, course_id)
                .orElseThrow(() -> new ResourceNotFoundException("Matricula não encontrada."));

        enrollmentsRepository.delete(enrollment);

        return new DeleteEnrollmentResponse(enrollment.getEnrollmentId(), "Matricula cancelada com sucesso");
    }

    @Transactional
    public DeleteEnrollmentResponse cancelAllEnrollmentByStudentId (Long student_id) {
        enrollmentsRepository.deleteByStudentId(student_id);
        return new DeleteEnrollmentResponse(student_id, "Todos os cursos vinculados ao aluno foram removidos com sucesso");
    }

    @Transactional
    public DeleteEnrollmentResponse cancelAllEnrollmentByCourseId (Long course_id) {
        enrollmentsRepository.deleteByCourseId(course_id);
        return new DeleteEnrollmentResponse(course_id, "Todos os alunos vinculados ao curso foram removidos com sucesso");
    }
}
