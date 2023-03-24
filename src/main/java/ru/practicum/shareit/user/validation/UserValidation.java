package ru.practicum.shareit.user.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.InternalServerError;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.model.User;

@Slf4j
@Component
public class UserValidation {

    public void create(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.warn("Валидация не пройдена: электронная почта не может быть пустой и должна содержать символ @");
            throw new ValidationException("электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getName() == null || user.getName().isBlank() || user.getName().contains(" ")) {
            log.warn("Валидация не пройдена: логин не может быть пустым и содержать пробелы ");
            throw new ValidationException("логин не может быть пустым и содержать пробелы ");
        }
    }

    public void duplicateEmail() {
        log.warn("Валидация не пройдена: электронная почта уже существует");
        throw new InternalServerError("электронная почта уже существует");
    }

    public void searchUser() {
        log.warn("Валидация не пройдена: Пользователь не найден");
        throw new NotFoundException("Пользователь не найден");
    }


}
