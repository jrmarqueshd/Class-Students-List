package com.junior.courses;

import com.junior.courses.dto.*;
import com.junior.enrollments.EnrollmentsRepository;
import com.junior.shared.exception.CourseHasStudentsException;
import com.junior.shared.exception.ResourceNotFoundException;
import com.junior.student.StudentEntity;
import com.junior.student.dto.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CourseService {
    private final CoursesRepository coursesRepository;

    private final EnrollmentsRepository enrollmentsRepository;

    public CourseService (
            CoursesRepository coursesRepository,
            EnrollmentsRepository enrollmentsRepository
    ) {
        this.coursesRepository = coursesRepository;
        this.enrollmentsRepository = enrollmentsRepository;
    }

    private CourseEntity getCourseById (Long id) {
        return coursesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso não encontrado"));
    }

    private void courseHasStudents (Long id) {
        if (enrollmentsRepository.existsByCourseId(id)) {
            throw new CourseHasStudentsException();
        }
    }

    public CreateCourseResponse create (CreateCourseRequest request) {
        CourseEntity course = new CourseEntity(
                request.name(),
                request.code(),
                request.description(),
                request.workload_hours(),
                request.active(),
                null
        );

        CourseEntity savedCourse = coursesRepository.save(course);

        return new CreateCourseResponse(savedCourse.getId(), "Curso cadastrado com sucesso");
    }

    public Page<CourseResponse> listAll (
            int page,
            int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        return coursesRepository.findAll(pageable)
                .map(CourseEntity::getCourseData);
    }

    public CourseResponse getById (Long id) {
        CourseEntity course = this.getCourseById(id);

        return course.getCourseData();
    }

    public UpdateCourseResponse updateById (Long id, CreateCourseRequest request) {
        CourseEntity course = this.getCourseById(id);

        course.update(
                request.name(),
                request.code(),
                request.description(),
                request.workload_hours(),
                request.active(),
                LocalDateTime.now()
        );

        coursesRepository.save(course);

        return new UpdateCourseResponse(course.getId(), "Curso atualizado com sucesso");
    }

    public DeleteCourseResponse deleteById (Long id) {
        CourseEntity course = this.getCourseById(id);

        this.courseHasStudents(id);

        coursesRepository.deleteById(course.getId());

        return new DeleteCourseResponse(id, "Curso deletado com sucesso");
    }

    public CourseStudentsResponse enrollmentStudent (Long id) {
        CourseEntity course = this.getCourseById(id);

        List<StudentResponse> courseStudents = enrollmentsRepository.findCourseStudentsById(id)
                .stream()
                .map(StudentEntity::getStudentData)
                .toList();

        return new CourseStudentsResponse(
                course.getId(),
                course.getName(),
                courseStudents
        );
    }
}
