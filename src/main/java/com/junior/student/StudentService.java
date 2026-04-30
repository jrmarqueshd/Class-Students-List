package com.junior.student;

import com.junior.shared.exception.EmailAlreadyExistsException;
import com.junior.student.dto.CreateStudentResponse;
import com.junior.student.dto.StudentResponse;
import com.junior.shared.exception.ResourceNotFoundException;
import com.junior.student.dto.DeleteStudentResponse;
import com.junior.student.dto.CreateStudentRequest;

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

        if (studentsRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException("E-mail já cadastrado");
        }

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
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado."));

        return new StudentResponse(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getBirthDate(),
                student.getCreatedAt()
        );
    }

    public StudentResponse updateById (Long id, CreateStudentRequest request) {
        StudentEntity student = studentsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado."));

        student.update(
                request.name(),
                request.email(),
                request.birth_date()
        );

        StudentEntity updateStudent = studentsRepository.save(student);

        return new StudentResponse(
                updateStudent.getId(),
                updateStudent.getName(),
                updateStudent.getEmail(),
                updateStudent.getBirthDate(),
                updateStudent.getCreatedAt()
        );
    }

    public DeleteStudentResponse deleteById (Long id) {
        StudentEntity student = studentsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado."));

        studentsRepository.deleteById(id);

        return new DeleteStudentResponse(id, "Aluno deletado com sucesso.");
    }
}
