package ru.practicum.shareit.user.validation;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.User;

class UserValidationTest {

    private UserValidation validation = new UserValidation();

    @Test
    void create() {
        User user = new User();
        user.setId(1);
        Assert.assertThrows(ValidationException.class, () -> validation.create(user));
        try {
            validation.create(user);
        } catch (Exception e) {
            Assert.assertEquals("электронная почта не может быть пустой и должна содержать символ @",
                    e.getMessage());
        }
        user.setEmail("test@mail");
        try {
            validation.create(user);
        } catch (Exception e) {
            Assert.assertEquals("логин не может быть пустым и содержать пробелы ",
                    e.getMessage());
        }

    }

    @Test
    void userNotFound() {
        try {
            validation.userNotFound();
        } catch (Exception e) {
            Assert.assertEquals("Пользователь не найден",
                    e.getMessage());
            return;
        }
    }
}