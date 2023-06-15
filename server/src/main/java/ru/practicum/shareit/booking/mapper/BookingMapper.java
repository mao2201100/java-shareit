package ru.practicum.shareit.booking.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.dto.BookingDtoOut;

@Component
public class BookingMapper {
    BookingMapper() {
    }

    public BookingDtoOut toBookingDtoOut(Booking booking) {
        if (booking.getItem() == null) throw new IllegalArgumentException("the Item parameter is missing");
        return new BookingDtoOut(booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                booking.getItem() != null ? booking.getItem().getId() : null,
                booking.getBooker().getId(),
                booking.getStatus(),
                booking.getItem().getName()
        );
    }
}



