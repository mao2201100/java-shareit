package ru.practicum.shareit.booking.dto;

import lombok.Data;
import ru.practicum.shareit.booking.Booking;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
public class BookingDtoOut {
    private long id;//уникальный идентификатор бронирования;

    private LocalDateTime start; // дата и время начала бронирования;
    private LocalDateTime end; // дата и время конца бронирования;
    private long itemId; // вещь, которую пользователь бронирует;
    private long bookerId; // пользователь, который осуществляет бронирование;
    private Booking.Status status;
    private String itemName;

    public BookingDtoOut(long id, LocalDateTime start, LocalDateTime end, long itemId, long bookerId, Booking.Status status, String itemName) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.itemId = itemId;
        this.bookerId = bookerId;
        this.status = status;
        this.itemName = itemName;
    }
}
