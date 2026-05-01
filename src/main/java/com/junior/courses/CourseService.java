package com.junior.courses;

import com.junior.courses.dto.*;
import com.junior.courses.CoursesRepository;
import com.junior.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CourseService {
    private final CoursesRepository coursesRepository;

    public CourseService (CoursesRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
    }

    private CourseEntity getCourseById (Long id) {
        return coursesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso não encontrado"));
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

    public List<CourseResponse> listAll () {
        return coursesRepository.findAll()
                .stream()
                .map(CourseEntity::get)
                .toList();
    }

    public CourseResponse getById (Long id) {
        CourseEntity course = this.getCourseById(id);

        return course.get();
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

        coursesRepository.deleteById(course.getId());

        return new DeleteCourseResponse(id, "Curso deletado com sucesso");
    }
}
