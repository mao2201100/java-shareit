package ru.practicum.shareit.exception;

public class UnsupportedStatus extends RuntimeException {
    public UnsupportedStatus() {
        super("Unknown state: UNSUPPORTED_STATUS");
    }
}
