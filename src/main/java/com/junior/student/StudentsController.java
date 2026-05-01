package com.junior.student;

import com.junior.student.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentsController {
    private final StudentService studentService;

    public StudentsController (StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateStudentResponse create (@RequestBody @Valid CreateStudentRequest request) {
        return studentService.create(request);
    }

    @GetMapping()
    public Page<StudentResponse> list (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return studentService.list(page, size);
    }

    @GetMapping("/{id}")
    public StudentResponse getById (@PathVariable Long id) {
        return studentService.getById(id);
    }

    @PutMapping("/{id}")
    public StudentResponse updateById (@PathVariable Long id, @RequestBody CreateStudentRequest request) {
        return studentService.updateById(id, request);
    }

    @DeleteMapping("/{id}")
    public DeleteStudentResponse deleteById (@PathVariable Long id) {
        return studentService.deleteById(id);
    }

    @GetMapping("/{id}/courses")
    public StudentCoursesResponse listStudentCourses (@PathVariable Long id) {
        return studentService.listStudentCourses(id);
    }
}
