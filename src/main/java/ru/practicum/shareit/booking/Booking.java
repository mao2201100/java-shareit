package ru.practicum.shareit.booking;

import lombok.Data;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
public class Booking {
    private Long id; // уникальный идентификатор бронирования
    public LocalDateTime start; // дата и время начала бронирования
    private LocalDateTime end; // дата и время конца бронирования
    private Item item; // вещь, которую пользователь бронирует
    private User booker; // пользователь, который осуществляет бронирование
    private String status; // статус бронирования. Может принимать одно из следующих
    //значений: WAITING — новое бронирование, ожидает одобрения, APPROVED —
    //Дополнительные советы ментора 2
    //бронирование подтверждено владельцем, REJECTED — бронирование
    //отклонено владельцем, CANCELED — бронирование отменено создателем
}
