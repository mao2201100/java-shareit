package ru.practicum.shareit.exception;

public class InternalServerError extends RuntimeException {
    public InternalServerError(String s) {
        super(s);
    }
}
