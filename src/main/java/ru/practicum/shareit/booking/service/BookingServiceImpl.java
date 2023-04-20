package ru.practicum.shareit.booking.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.validation.BookingValidation;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.UnsupportedStatus;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.request.Request;
import ru.practicum.shareit.request.RequestRepository;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class BookingServiceImpl implements BookingService {
    private UserService userService;
    private BookingValidation bookingValidation;
    private BookingRepository bookingRepository;
    private UserRepository userRepository;
    private RequestRepository requestRepository;
    private ItemRepository itemRepository;
    private ItemServiceImpl itemService;

    public BookingServiceImpl(UserService userService, BookingRepository bookingRepository,
                              UserRepository userRepository, BookingValidation bookingValidation,
                              RequestRepository requestRepository, ItemRepository itemRepository,
                              ItemServiceImpl itemService) {
        this.userService = userService;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.bookingValidation = bookingValidation;
        this.requestRepository = requestRepository;
        this.itemRepository = itemRepository;
        this.itemService = itemService;
    }

    @Override
    public Booking bookingId(long bookingId, long ownerId) {
        userService.searchUser(ownerId);
        searchBooking(bookingId);
        return bookingRepository.findById(bookingId).orElseThrow(() -> {
            throw new UnsupportedStatus();
        });
    }

    @Override
    public Collection<Booking> bookingsUser(String state, long bookerId) { //Получение списка всех бронирований
        // текущего пользователя
        userService.searchUser(bookerId);
        switch (state) { // Бронирования должны возвращаться отсортированными по дате от более новых к более старым.
            case ("CURRENT"): //Текущие бронирования пользователя
                return bookingRepository.fetchBookingByStateCurrentByBookerId(bookerId);
            case ("PAST"):// завершённые
                return bookingRepository.fetchBookingByStatePastByBookerId(bookerId);
            case ("FUTURE")://будущие
                return bookingRepository.fetchBookingByStateFutureByBookerId(bookerId);
            case ("WAITING")://ожидающие подтверждения
            case ("REJECTED"): //отклоненные
                return bookingRepository.fetchBookingByStatusByBookerId(bookerId, state);
            case ("ALL"):
                return bookingRepository.fetchBookingByBookerId(bookerId); // на случай если state пусто или All
            default:
                throw new UnsupportedStatus();
        }

    }

    @Override
    public Collection<Booking> bookingsOwner(String state, long ownerId) { //Получение списка бронирований для всех
        // вещей текущего пользователя. Этот запрос имеет смысл для владельца хотя бы одной вещи
        userService.searchUser(ownerId);
        switch (state) { // Бронирования должны возвращаться отсортированными по дате от более новых к более старым.
            case ("CURRENT"): //Текущие бронирования пользователя
                return bookingRepository.fetchBookingByStateCurrentByOwnerId(ownerId);
            case ("PAST"):// завершённые
                return bookingRepository.fetchBookingByStatePastByOwnerId(ownerId);
            case ("FUTURE")://будущие
                return bookingRepository.fetchBookingByStateFutureByOwnerId(ownerId);
            case ("WAITING")://ожидающие подтверждения
            case ("REJECTED"): //отклоненные
                return bookingRepository.fetchBookingByStatusByOwnerId(ownerId, state);
            case ("ALL"):
                return bookingRepository.fetchBookingOwnerId(ownerId); // на случай если state пусто или All
            default:
                throw new UnsupportedStatus();
        }
    }

    @Override
    public Booking create(BookingDto dto, Long bookerId) { //Добавление бронирования
        userService.searchUser(bookerId);
        itemService.checkItem(dto.getItemId());
        itemService.checkItemAvailable(dto.getItemId());
        itemService.checkItemOwner(dto.getItemId(), bookerId);
        dateValidation(dto);
        Request request = new Request();
        request.setRequestorId(bookerId);
        requestRepository.saveAndFlush(request);
        Booking booking = new Booking();
        BeanUtils.copyProperties(dto, booking);
        booking.setBooker(userRepository.findById(bookerId).orElseThrow());
        booking.setStatus(Booking.Status.WAITING);
        booking.setItem(itemRepository.findById(dto.getItemId()).orElseThrow());
        return save(booking);
    }

    @Transactional
    public Booking save(Booking booking) {
        return bookingRepository.saveAndFlush(booking);
    }

    @Override
    @Transactional
    public Booking approvedOrRejected(Boolean approved, long ownerId, long bookingId) { //Подтверждение /
        // отклонение бронирования
        Booking booking = bookingRepository.getById(bookingId);
        Long ownerId2 = itemRepository.getById(booking.getItem().getId()).getOwnerId();
        if (ownerId != ownerId2) {
            throw new NotFoundException("");
        }
        if (booking.getStatus() != fetch(approved)) {
            if (approved) {
                booking.setStatus(Booking.Status.APPROVED);
            } else {
                booking.setStatus(Booking.Status.REJECTED);
            }
            return save(booking);
        } else {
            bookingValidation.approvederСheck();
        }
        return null;
    }

    private Booking.Status fetch(boolean state) {
        if (state) {
            return Booking.Status.APPROVED;
        } else {
            return Booking.Status.REJECTED;
        }
    }

    @Override
    public Booking getBookingId(long bookingId, long userId) {
        userService.searchUser(userId);
        searchBooking(bookingId);

        Booking booking = bookingRepository.findById(bookingId).orElse(null);

        if (booking != null && (
                booking.getBooker().getId() == userId || booking.getItem().getOwnerId() == userId)) {
            // Проверка на автора бронирования, либо владельца вещи,
            // к которой относится бронирование.
            return booking;
        }
        bookingValidation.checkGetBookingId();
        return null;
    }

    public void searchBooking(long bookingId) { // проверка существования бронирования
        if (bookingRepository.findById(bookingId).isEmpty()) {
            bookingValidation.bookingNotFound();
        }
    }

    public void dateValidation(BookingDto booking) {
        if (booking.getEnd() == null || booking.getStart() == null ||
                booking.getEnd().isBefore(booking.getStart()) || booking.getEnd().equals(booking.getStart()) ||
                booking.getStart().isBefore(LocalDateTime.now())) {
            bookingValidation.dateValidation();
        }
    }
}
