package com.junior.controller;

import com.junior.dto.CreateStudentRequest;
import com.junior.dto.CreateStudentResponse;
import com.junior.dto.StudentResponse;
import com.junior.service.StudentService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public CreateStudentResponse create (@RequestBody CreateStudentRequest request) {
        return studentService.create(request);
    }

    @GetMapping()
    public List<StudentResponse> list () {
        return studentService.list();
    }

    @GetMapping("/{id}")
    public StudentResponse getById (@PathVariable Long id) {
        return studentService.getById(id);
    }
}
