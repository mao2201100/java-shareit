package ru.practicum.shareit.item.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.model.Item;

@Slf4j
@Component
public class ItemValidation {

    public void item(Item item) {
        if (item.getAvailable() == null) {
            log.warn("Валидация не пройдена: не указан статус вещи");
            throw new ValidationException("не указан статус вещи");
        }
        if (item.getDescription() == null || item.getDescription().isEmpty()) {
            log.warn("Валидация не пройдена: не указано описание");
            throw new ValidationException("не указано описание");
        }
        if (item.getName() == null || item.getName().isEmpty()) {
            log.warn("Валидация не пройдена: нет указано название вещи");
            throw new ValidationException("нет указано название вещи");
        }
    }

    public void searchItem() {
        log.warn("Валидация не пройдена: вещь не найдена");
        throw new NotFoundException("вещь не найдена");
    }

    public void ownerItem(String owner, String ownerItem) {
        if (!(owner.equals(ownerItem))) {
            log.warn("Валидация не пройдена: не правильно указан id хозяина вещи");
            throw new NotFoundException("не правильно указан id хозяина вещи");
        }

    }
}

