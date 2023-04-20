package ru.practicum.shareit.booking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.Collection;

/**
 * TODO Sprint add-bookings.
 *
 */
@Slf4j
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;
    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }
    @PostMapping
    public Booking create(@RequestBody BookingDto booking, @RequestHeader("X-Sharer-User-Id") long bookerId){
        // Добавление нового запроса на бронирование
        log.info("Executing Comment create");
        return bookingService.create(booking, bookerId);
    }

    @PatchMapping("/{bookingId}")
    public Booking approvedOrRejected(@RequestParam(name = "approved") Boolean approved,
                                      @RequestHeader("X-Sharer-User-Id") long ownerId,
                                      @PathVariable("bookingId") Long bookingId){
        log.info("Executing Approved or rejected status");
        return bookingService.approvedOrRejected(approved, ownerId,bookingId);
    }


    @GetMapping("") //Получение списка всех бронирований текущего пользователя
    public Collection<Booking> bookingsUser(@RequestParam(name = "state",  required = false, defaultValue = "ALL") String state,
                                            @RequestHeader("X-Sharer-User-Id") long userId){
        log.info("Executing Get bookingsUserId: " + userId);
        return bookingService.bookingsUser(state, userId);
    }

    @GetMapping("/owner") //Получение списка бронирований для всех вещей текущего пользователя
    public Collection<Booking> bookingsOwner(@RequestParam(required = false, defaultValue = "ALL") String state,
                                             @RequestHeader("X-Sharer-User-Id") long userId){
        log.info("Executing Get bookingsOwner: " + userId);
        return bookingService.bookingsOwner(state, userId);
    }

    @GetMapping("/{bookingId}")
    public Booking bookingId(@PathVariable("bookingId") Long bookingId, @RequestHeader("X-Sharer-User-Id")
    long userId){
        log.info("Executing Get bookingId: " + bookingId);
        return bookingService.getBookingId(bookingId, userId);
    }


}
