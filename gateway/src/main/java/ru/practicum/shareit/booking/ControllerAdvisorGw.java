package ru.practicum.shareit.booking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ControllerAdvisorGw extends ResponseEntityExceptionHandler {


    @ExceptionHandler(UnsupportedStatus.class)
    public ResponseEntity<Object> unsupportedStatus(UnsupportedStatus ex) {
        Map<String, String> body = new LinkedHashMap<>();
        body.put("timestamp", "Что-то пошло не так");
        body.put("error", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
