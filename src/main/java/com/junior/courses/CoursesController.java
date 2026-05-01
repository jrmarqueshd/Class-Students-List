package com.junior.courses;

import com.junior.courses.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CoursesController {
    private final CourseService courseService;

    public CoursesController (CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCourseResponse create (@RequestBody @Valid CreateCourseRequest request) {
        return courseService.create(request);
    }

    @GetMapping
    public List<CourseResponse> listAll () {
        return courseService.listAll();
    }

    @GetMapping("/{id}")
    public CourseResponse getById (@PathVariable Long id) {
        return courseService.getById(id);
    }

    @PutMapping("/{id}")
    public UpdateCourseResponse getById (@PathVariable Long id, @RequestBody @Valid CreateCourseRequest request) {
        return courseService.updateById(id, request);
    }

    @DeleteMapping("/{id}")
    public DeleteCourseResponse deleteById (@PathVariable Long id) {
        return courseService.deleteById(id);
    }
}
