package ru.practicum.shareit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleCityNotFoundException(
            ValidationException ex, WebRequest request) {

        Map<String, String> body = new LinkedHashMap<>();
        body.put("error", "Что-то пошло не так");
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNodataFoundException(
            NotFoundException ex, WebRequest request) {

        Map<String, String> body = new LinkedHashMap<>();
        body.put("timestamp", "Что-то пошло не так");
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<Object> handleNodataFoundException(
            InternalServerError ex, WebRequest request) {

        Map<String, String> body = new LinkedHashMap<>();
        body.put("serverError", "Что-то пошло не так");
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
