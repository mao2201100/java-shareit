package ru.practicum.shareit.booking.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.BookingStatus;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BookingDtoOutTest {
    private static BookingDto bookingDto = new BookingDto();

    @BeforeAll
    static void setup() {
        bookingDto.setId(1L);
        bookingDto.setBookerId(1L);
        bookingDto.setEnd(LocalDateTime.of(2020, 06, 05, 10, 00, 00));
        bookingDto.setStart(LocalDateTime.of(2020, 07, 05, 10, 00, 00));
        bookingDto.setItemId(1L);
        bookingDto.setStatus(BookingStatus.APPROVED);
    }


    @Test
    void getId() {
        assertEquals(1L, bookingDto.getId());
    }

    @Test
    void getStart() {
        assertEquals(LocalDateTime.of(2020, 07, 05, 10, 00, 00),
                bookingDto.getStart());

    }

    @Test
    void getEnd() {
        assertEquals(LocalDateTime.of(2020, 07, 05, 10, 00, 00),
                bookingDto.getStart());

    }

    @Test
    void getItemId() {
        assertEquals(1L, bookingDto.getItemId());

    }

    @Test
    void getBookerId() {
        assertEquals(1L, bookingDto.getBookerId());

    }

    @Test
    void getStatus() {
        assertEquals(BookingStatus.APPROVED, bookingDto.getStatus());

    }
}