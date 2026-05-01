package com.junior.shared.exception;

public class CourseHasStudentsException extends RuntimeException {
    public CourseHasStudentsException() {
        super("Este curso possui alunos inscritos, cancele o curso antes de excluí-lo.");
    }
}
