package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.Collection;

public interface BookingService {
    Booking bookingId(long bookingId, long ownerId);

    Collection<Booking> bookingsUser(String state, long userId, Long from, Long size);

    Collection<Booking> bookingsOwner(String state, long userId, Long from, Long size);

    Booking create(BookingDto dto, Long bookerId);

    Booking approvedOrRejected(Boolean approved, long ownerId, long bookingId);

    Booking getBookingId(long bookingId, long userId);

}
