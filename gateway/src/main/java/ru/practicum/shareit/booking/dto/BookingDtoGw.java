package ru.practicum.shareit.booking.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
public class BookingDtoGw {
    private long id;//уникальный идентификатор бронирования;
    private LocalDateTime start; // дата и время начала бронирования;
    private LocalDateTime end; // дата и время конца бронирования;
    private long itemId; // вещь, которую пользователь бронирует;
    private long bookerId; // пользователь, который осуществляет бронирование;
    private BookingState status;

}
