package ru.practicum.shareit.booking.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;

@Slf4j
@Component
public class BookingValidation {

    public void bookingNotFound() {
        log.warn("Валидация не пройдена: Бронирование не найдено");
        throw new NotFoundException("Бронирование не найдено");
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

    public void bookingIdIsFirstAndSizeIndex(Long from, Long size) {
        if (from < 0 && size < 0) {
            log.warn("Валидация не пройдена: не верно указан индекс первого элемента");
            throw new ValidationException("не верно указан индекс первого элемента");
        }
        if (from == 0 && size == 0) {
            log.warn("Валидация не пройдена: не верно указано количество элементов при выводе");
            throw new ValidationException("не верно указано количество элементов при выводе");
        }
    }
}
