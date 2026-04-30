package com.junior.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFound (ResourceNotFoundException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        problemDetail.setTitle("Resource not found");
        problemDetail.setDetail(exception.getMessage());
        problemDetail.setType(URI.create("/errors/resource-not-found"));

        return problemDetail;
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ProblemDetail handleEmailAlreadyExistsException (EmailAlreadyExistsException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);

        problemDetail.setTitle("Duplicated email");
        problemDetail.setDetail("O email já existe na base de dados");
        problemDetail.setType(URI.create("/errors/email-already-exists"));

        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException (MethodArgumentNotValidException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        List<Map<String, String>> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> Map.of(
                        "field", error.getField(),
                        "message", Optional.ofNullable(error.getDefaultMessage()).orElse("É inválido.")
                ))
                .toList();

        problemDetail.setTitle("Validation error");
        problemDetail.setDetail("Um ou mais campos falharam");
        problemDetail.setType(URI.create("/errors/validation-error"));
        problemDetail.setProperty("errors", errors);

        return problemDetail;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleNotReadableException (HttpMessageNotReadableException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        String message = "Requisição inválida";

        Throwable cause = exception.getMostSpecificCause();

        if (cause instanceof java.time.format.DateTimeParseException) {
            message = "A data precisa ter o seguinte padrão yyyy-MM-dd";
        }

        problemDetail.setTitle("Validation error");
        problemDetail.setDetail("Verifique os campos informados na requisição");
        problemDetail.setType(URI.create("/errors/validation-error"));
        problemDetail.setProperty("moreDetails", message);

        return problemDetail;
    }
}
