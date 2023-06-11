package ru.practicum.shareit.booking.validation;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;

class BookingValidationTest {
    private BookingValidation bookingValidation = new BookingValidation();

    @Test
    void bookingNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> bookingValidation.bookingNotFound());
        try {
            bookingValidation.bookingNotFound();
        } catch (Exception e) {
            Assert.assertEquals("Бронирование не найдено",
                    e.getMessage());
        }
    }

    @Test
    void approvederСheck() {
        Assert.assertThrows(ValidationException.class, () -> bookingValidation.approvederСheck());
        try {
            bookingValidation.approvederСheck();
        } catch (Exception e) {
            Assert.assertEquals("Бронирование может подтвердить только хозяин",
                    e.getMessage());
        }
    }

    @Test
    void dateValidation() {
        Assert.assertThrows(ValidationException.class, () -> bookingValidation.dateValidation());
        try {
            bookingValidation.dateValidation();
        } catch (Exception e) {
            Assert.assertEquals("Введены не верные даты бронирования",
                    e.getMessage());
        }
    }

    @Test
    void checkGetBookingId() {
        Assert.assertThrows(NotFoundException.class, () -> bookingValidation.checkGetBookingId());
        try {
            bookingValidation.checkGetBookingId();
        } catch (Exception e) {
            Assert.assertEquals("Запрос может сделать хозяин или арендатор вещи",
                    e.getMessage());
        }
    }

    @Test
    void bookingIdIsFirstAndSizeIndex() {
        Assert.assertThrows(ValidationException.class, () -> bookingValidation.bookingIdIsFirstAndSizeIndex(-1L, -8L));
        try {
            bookingValidation.bookingIdIsFirstAndSizeIndex(-1L, -8L);
        } catch (Exception e) {
            Assert.assertEquals("не верно указан индекс первого элемента",
                    e.getMessage());
        }

        Assert.assertThrows(ValidationException.class, () -> bookingValidation.bookingIdIsFirstAndSizeIndex(0L, 0L));
        try {
            bookingValidation.bookingIdIsFirstAndSizeIndex(0L, 0L);
        } catch (Exception e) {
            Assert.assertEquals("не верно указано количество элементов при выводе",
                    e.getMessage());
        }
    }
}