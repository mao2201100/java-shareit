package ru.practicum.shareit.booking.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;

@Slf4j
@Component
public class BookingValidation {

    public void bookingNotFound() {
        log.warn("Валидация не пройдена: Брорирование не найдено");
        throw new NotFoundException("Брорирование не найдено");
    }

    public void approvederСheck() {
        log.warn("Валидация не пройдена: Брорирование может подтвердить только хозяин");
        throw new ValidationException("Бронирование может подтвердить только хозяин");
    }

    public void dateValidation() {
        log.warn("Введены не верные даты бронирования");
        throw new ValidationException("Введены не верные даты бронирования");
    }

    public void checkGetBookingId() {
        log.warn("Валидация не пройдена: Запрос может сделать хозяин или арендатор вещи");
        throw new NotFoundException("Запрос может сделать хозяин или арендатор вещи");
    }
}
