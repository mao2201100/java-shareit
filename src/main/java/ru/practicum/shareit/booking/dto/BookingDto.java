package ru.practicum.shareit.booking.dto;

import lombok.Data;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingStatus;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
public class BookingDto {
    private long id;//уникальный идентификатор бронирования;
    private LocalDateTime start; // дата и время начала бронирования;
    private LocalDateTime end; // дата и время конца бронирования;
    private long itemId; // вещь, которую пользователь бронирует;
    private long bookerId; // пользователь, который осуществляет бронирование;
    private BookingStatus status;

}
