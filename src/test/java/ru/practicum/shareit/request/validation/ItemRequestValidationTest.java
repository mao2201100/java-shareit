package ru.practicum.shareit.request.validation;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.request.Request;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class ItemRequestValidationTest {
    private ItemRequestValidation itemRequestValidation = new ItemRequestValidation();
    private static Request request = new Request();

    @BeforeAll
    static void setup() {
        request.setId(1);
        request.setCreated(Timestamp.valueOf("2023-05-22 23:00:29.0"));
        request.setRequestorId(1L);
    }

    @Test
    void itemRequest() {
        Assert.assertThrows(ValidationException.class, () -> itemRequestValidation.itemRequest(request));
        try {
            itemRequestValidation.itemRequest(request);
        } catch (Exception e) {
            Assert.assertEquals("не указано описание",
                    e.getMessage());
        }
    }

    @Test
    void itemRequestIdIsEmpty() {
        Assert.assertThrows(NotFoundException.class, () -> itemRequestValidation.itemRequestIdIsEmpty(false));
        try {
            itemRequestValidation.itemRequestIdIsEmpty(false);
        } catch (Exception e) {
            Assert.assertEquals("такого id запроса не существует",
                    e.getMessage());
        }
    }

    @Test
    void itemRequestIdIsFirstAndSizeIndex() {
        Assert.assertThrows(ValidationException.class, () -> itemRequestValidation.itemRequestIdIsFirstAndSizeIndex(-1L, -2L));
        try {
            itemRequestValidation.itemRequestIdIsFirstAndSizeIndex(-1L, -2L);
        } catch (Exception e) {
            Assert.assertEquals("не верно указан индекс первого элемента",
                    e.getMessage());
        }

        Assert.assertThrows(ValidationException.class, () -> itemRequestValidation.itemRequestIdIsFirstAndSizeIndex(0L, 0L));
        try {
            itemRequestValidation.itemRequestIdIsFirstAndSizeIndex(0L, 0L);
        } catch (Exception e) {
            Assert.assertEquals("не верно указано количество элементов при выводе",
                    e.getMessage());
        }
    }
}