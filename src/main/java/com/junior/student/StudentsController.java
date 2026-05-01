package com.junior.student;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.junior.student.dto.CreateStudentRequest;
import com.junior.student.dto.CreateStudentResponse;
import com.junior.student.dto.DeleteStudentResponse;
import com.junior.student.dto.StudentResponse;

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
    public List<StudentResponse> list () {
        return studentService.list();
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
}
