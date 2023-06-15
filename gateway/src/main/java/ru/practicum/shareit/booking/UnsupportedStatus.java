package ru.practicum.shareit.booking;

public class UnsupportedStatus extends RuntimeException {
    public UnsupportedStatus() {
        super("Unknown state: UNSUPPORTED_STATUS");
    }
}
