package com.junior.shared.exception;

public class StudentHasEnrollmentException extends RuntimeException {
    public StudentHasEnrollmentException() {
        super("Este aluno possui inscrições em cursos, exclua o vinculo antes de exclui-lo.");
    }
}
