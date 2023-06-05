package ru.practicum.shareit.booking.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookingDtoOutTest {
    private static Item item = new Item();
    private static BookingDtoOut bookingDtoOut = new BookingDtoOut(1L,
            LocalDateTime.of(2020, 06, 05, 10, 00, 00),
            LocalDateTime.of(2020, 07, 05, 10, 00, 00),
            1L, 1L, BookingStatus.APPROVED, "testName");

    @BeforeAll
    static void setup() {
        item.setId(1L);
        item.setName("testName");
        item.setAvailable(true);
        item.setOwnerId(2L);
    }

    @Test
    void getStatus() {
        assertEquals(BookingStatus.APPROVED, bookingDtoOut.getStatus());
    }

    @Test
    void getItemName() {
        assertEquals("testName", bookingDtoOut.getItemName());
    }
}