package ru.practicum.shareit.request.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.request.Request;

@Slf4j
@Component
public class ItemRequestValidation {
    public void itemRequest(Request request) {
        if (request.getDescription() == null) {
            log.warn("Валидация не пройдена: не указано описание");
            throw new ValidationException("не указано описание");
        }
    }

    public void itemRequestIdIsEmpty(Boolean empty) {
        if (empty == false) {
            log.warn("Валидация не пройдена: такого id запроса не существует");
            throw new NotFoundException("такого id запроса не существует");
        }
    }

    public void itemRequestIdIsFirstAndSizeIndex(Long index, Long size) {
        if (index < 0 && size < 0) {
            log.warn("Валидация не пройдена: не верно указан индекс первого элемента");
            throw new ValidationException("не верно указан индекс первого элемента");
        }
        if (index == 0 && size == 0) {
            log.warn("Валидация не пройдена: не верно указано количество элементов при выводе");
            throw new ValidationException("не верно указано количество элементов при выводе");
        }
    }
}
