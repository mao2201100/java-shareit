package ru.practicum.shareit.booking.service;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.UnsupportedStatus;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
class BookingServiceImplTest {
    @MockBean
    private UserServiceImpl userService;
    @MockBean
    private BookingRepository bookingRepository;
    @MockBean
    private ItemServiceImpl itemServiceImpl;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private ItemRepository itemRepository;

    @Autowired
    private BookingServiceImpl bookingService;

    private static Booking booking = new Booking();
    private static Booking bookingBefore = new Booking();
    private static BookingDto bookingDto = new BookingDto();
    private static Item item = new Item();
    private static Item item1 = new Item();
    private static final User user = new User();
    private static final LocalDateTime startTime = LocalDateTime.now().plusMinutes(3);
    private static final LocalDateTime startTimeBookingBefore = LocalDateTime.now().minusHours(2L);
    private static final LocalDateTime endTime = LocalDateTime.now().plusHours(1L);
    private static final LocalDateTime endTimeBookingBefore = LocalDateTime.now().minusHours(1L);

    @BeforeAll
    static void setup() {
        user.setId(1L);
        user.setName("testUser");
        user.setEmail("jepp@ebrilo.test");

        booking.setId(2L);
        booking.setStart(startTime);
        booking.setEnd(endTime);
        booking.setBooker(user);
        booking.setStatus(BookingStatus.REJECTED);
        booking.setItem(item);

        bookingBefore.setId(1L);
        bookingBefore.setStart(startTimeBookingBefore);
        bookingBefore.setEnd(endTimeBookingBefore);
        bookingBefore.setBooker(user);
        bookingBefore.setStatus(BookingStatus.APPROVED);
        bookingBefore.setItem(item1);

        bookingDto.setId(2L);
        bookingDto.setStart(startTime);
        bookingDto.setEnd(endTime);
        bookingDto.setBookerId(user.getId());
        bookingDto.setStatus(BookingStatus.REJECTED);
        bookingDto.setItemId(5L);

        item.setId(1L);
        item.setOwner("testUser");
        item.setDescription("testItemDescription");
        item.setAvailable(true);
        item.setOwnerId(user.getId());

        item1.setId(2L);
        item1.setOwner("testUser");
        item1.setDescription("testItemDescription");
        item1.setAvailable(true);
        item1.setOwner("testUser555");
        item1.setOwnerId(555L);
    }

    @Test
    void bookingId() {
        Mockito.doNothing()
                .when(userService).searchUser(1L);

        Mockito
                .when(bookingRepository.findById(booking.getId()))
                .thenReturn(Optional.ofNullable(booking));

        Mockito
                .when(bookingRepository.findById(Mockito.argThat(arg -> arg > 2L)))
                .thenThrow(new UnsupportedStatus());


        Booking result = bookingService.bookingId(booking.getId(), user.getId());
        Assert.assertEquals(1L, result.getBooker().getId());
        Assert.assertEquals(2L, result.getId());
        Assert.assertEquals(startTime, result.getStart());
        Assert.assertEquals(endTime, result.getEnd());
        Assert.assertThrows(UnsupportedStatus.class, () -> bookingService.bookingId(3, user.getId()));
    }

    @Test
    void bookingIdBeadBooking() {
        Mockito
                .when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        Assert.assertThrows(NotFoundException.class, () -> bookingService.bookingId(55L, 55L));
        try {
            bookingService.bookingId(55L, 55L);
        } catch (Exception e) {
            Assert.assertEquals("Бронирование не найдено",
                    e.getMessage());
        }
    }

    @Test
    void bookingsUser() {
        List<Booking> bookingRejected = List.of(booking);
        List<Booking> bookingAll = List.of(bookingBefore, booking);
        List<Booking> bookingEmpty = List.of();

        Mockito.doNothing()
                .when(userService).searchUser(Mockito.anyLong());

        Mockito
                .when(bookingRepository.fetchBookingByStateCurrentByBookerId(user.getId()))
                .thenReturn(Collections.emptyList());
        Mockito
                .when(bookingRepository.fetchBookingByStatePastByBookerId(user.getId()))
                .thenReturn(Collections.emptyList());
        Mockito
                .when(bookingRepository.fetchBookingByStateFutureByBookerId(user.getId()))
                .thenReturn(Collections.emptyList());
        Mockito
                .when(bookingRepository.fetchBookingByStatusByBookerId(user.getId(), "REJECTED"))
                .thenReturn(List.of(booking));
        Mockito
                .when(bookingRepository.fetchBookingByBookerId(user.getId()))
                .thenReturn(List.of(bookingBefore, booking));

        List<Booking> bookingsUserTest = (List) bookingService.bookingsUser("REJECTED", 1, null, null);

        Assert.assertEquals(bookingRejected, bookingsUserTest);

        List<Booking> bookingsUserTest2 = (List) bookingService.bookingsUser("ALL", 1, null, null);

        Assert.assertEquals(bookingAll, bookingsUserTest2);

        List<Booking> bookingsUserEmpty = (List) bookingService.bookingsUser("FUTURE", 1, null, null);

        Assert.assertEquals(bookingEmpty, bookingsUserEmpty);

        Assert.assertThrows(UnsupportedStatus.class, () -> bookingService.bookingsUser("XXX", 1, null, null));
    }

    @Test
    void bookingsOwner() {
        List<Booking> bookingRejected = List.of(booking);
        List<Booking> bookingAll = List.of(bookingBefore, booking);
        List<Booking> bookingEmpty = List.of();

        Mockito.doNothing()
                .when(userService).searchUser(Mockito.anyLong());

        Mockito
                .when(bookingRepository.fetchBookingByStateCurrentByOwnerId(user.getId()))
                .thenReturn(Collections.emptyList());
        Mockito
                .when(bookingRepository.fetchBookingByStatePastByOwnerId(user.getId()))
                .thenReturn(Collections.emptyList());
        Mockito
                .when(bookingRepository.fetchBookingByStateFutureByOwnerId(user.getId()))
                .thenReturn(Collections.emptyList());
        Mockito
                .when(bookingRepository.fetchBookingByStatusByOwnerId(user.getId(), "REJECTED"))
                .thenReturn(List.of(booking));
        Mockito
                .when(bookingRepository.fetchBookingOwnerId(user.getId()))
                .thenReturn(List.of(bookingBefore, booking));

        List<Booking> bookingsUserTest = (List) bookingService.bookingsOwner("REJECTED", 1, null, null);

        Assert.assertEquals(bookingRejected, bookingsUserTest);

        List<Booking> bookingsUserTest2 = (List) bookingService.bookingsOwner("ALL", 1, null, null);

        Assert.assertEquals(bookingAll, bookingsUserTest2);

        List<Booking> bookingsUserEmpty = (List) bookingService.bookingsOwner("FUTURE", 1, null, null);

        Assert.assertEquals(bookingEmpty, bookingsUserEmpty);

        Assert.assertThrows(UnsupportedStatus.class, () -> bookingService.bookingsOwner("XXX", 1, null, null));

    }

    @Test
    void create() {
        Mockito
                .when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(user));
        Mockito
                .when(itemRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(item));
        Mockito
                .when(bookingRepository.saveAndFlush(Mockito.any(Booking.class)))
                .thenReturn(booking);

        Booking bookingResult = bookingService.create(bookingDto, user.getId());

        Mockito
                .verify(bookingRepository, Mockito.times(1))
                .saveAndFlush(Mockito.any(Booking.class));

        assertEquals(2L, bookingResult.getId());
        assertEquals(startTime, bookingResult.getStart());
        assertEquals(endTime, bookingResult.getEnd());
        assertEquals(BookingStatus.WAITING, bookingResult.getStatus());
        assertEquals(1L, bookingResult.getItem().getId());

        Assert.assertEquals(BookingStatus.WAITING, bookingResult.getStatus());
        Assert.assertSame(user, bookingResult.getBooker());
        Assert.assertSame(item, bookingResult.getItem());
    }

    @Test
    void saveBooking() {
        Mockito
                .when(bookingService.saveBooking(Mockito.any(Booking.class)))
                .thenReturn(booking);

        bookingService.saveBooking(booking);

        Mockito
                .verify(bookingRepository, Mockito.times(1))
                .saveAndFlush(Mockito.any(Booking.class));
    }

    @Test
    void approvedOrRejected() {
        Mockito
                .when(bookingRepository.getById(Mockito.anyLong()))
                .thenReturn(booking);
        Mockito
                .when(itemRepository.getById(Mockito.anyLong()))
                .thenReturn(item);

        bookingService.approvedOrRejected(true, 1L, 2L);

        Assert.assertEquals(BookingStatus.APPROVED, booking.getStatus());

        bookingService.approvedOrRejected(false, 1L, 2L);

        Assert.assertEquals(BookingStatus.REJECTED, booking.getStatus());
    }

    @Test
    void approvedOrRejectedValidation() {
        boolean state = true;
        Mockito
                .when(bookingRepository.getById(Mockito.anyLong()))
                .thenReturn(booking);
        Mockito
                .when(itemRepository.getById(Mockito.anyLong()))
                .thenReturn(item);

        Assert.assertThrows(NotFoundException.class, () -> bookingService.approvedOrRejected(state, 555L, 2L));
    }

    @Test
    void getBookingId() {
        Mockito
                .when(bookingRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(booking));

        Booking bookingResult = bookingService.getBookingId(booking.getId(), user.getId());
        Assert.assertSame(booking, bookingResult);

        Assert.assertThrows(NotFoundException.class, () -> bookingService.getBookingId(777L, user.getId()));
    }

    @Test
    void getBookingIdBeadBooking() {
        Mockito
                .when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        Assert.assertThrows(NotFoundException.class, () -> bookingService.bookingId(55L, 55L));
        try {
            bookingService.bookingId(55L, 55L);
        } catch (Exception e) {
            Assert.assertEquals("Бронирование не найдено",
                    e.getMessage());
        }
    }

    @Test
    void searchBooking() {
        Mockito
                .when(bookingRepository.findById(5L))
                .thenReturn(Optional.empty());

        Assert.assertThrows(NotFoundException.class, () -> bookingService.searchBooking(5L));
        try {
            bookingService.searchBooking(5L);
        } catch (Exception e) {
            Assert.assertEquals("Бронирование не найдено",
                    e.getMessage());
        }
    }

    @Test
    void dateValidation() {
        BookingDto bookingDateValidation = new BookingDto();
        bookingDateValidation.setId(3L);
        bookingDateValidation.setStart(LocalDateTime.now());
        bookingDateValidation.setEnd(LocalDateTime.now().minusHours(1));
        bookingDateValidation.setBookerId(user.getId());
        bookingDateValidation.setStatus(BookingStatus.REJECTED);
        bookingDateValidation.setItemId(1);

        Assert.assertThrows(ValidationException.class, () -> bookingService.dateValidation(bookingDateValidation));
        try {
            bookingService.dateValidation(bookingDateValidation);
        } catch (Exception e) {
            Assert.assertEquals("Введены не верные даты бронирования",
                    e.getMessage());
        }
    }

    @Test
    void save() {
        bookingService.saveBooking(booking);
        Mockito
                .verify(bookingRepository, Mockito.times(1)).saveAndFlush(Mockito.any(Booking.class));
    }
}