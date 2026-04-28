package com.junior.service;

import com.junior.dto.CreateStudentResponse;
import com.junior.dto.StudentResponse;
import com.junior.entity.StudentEntity;
import com.junior.repository.StudentsRepository;
import com.junior.dto.CreateStudentRequest;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentsRepository studentsRepository;

    public StudentService (StudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;
    }

    public CreateStudentResponse create (CreateStudentRequest request) {
        StudentEntity student = new StudentEntity(
                request.name(),
                request.email(),
                request.birth_date()
        );

        StudentEntity savedStudent = studentsRepository.save(student);

        return new CreateStudentResponse(savedStudent.getId(), "Aluno cadastrado com sucesso.");
    }

    public List<StudentResponse> list () {
        return studentsRepository.findAll()
                .stream()
                .map(student -> new StudentResponse(
                        student.getId(),
                        student.getName(),
                        student.getEmail(),
                        student.getBirthDate(),
                        student.getCreatedAt()
                ))
                .toList();
    }

    public StudentResponse getById (Long id) {
        StudentEntity student = studentsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado."));

        return new StudentResponse(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getBirthDate(),
                student.getCreatedAt()
        );
    }
}
