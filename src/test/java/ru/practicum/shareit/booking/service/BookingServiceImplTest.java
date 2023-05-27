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
import ru.practicum.shareit.booking.validation.BookingValidation;
import ru.practicum.shareit.exception.UnsupportedStatus;
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


@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
class BookingServiceImplTest {
    @MockBean
    private UserServiceImpl userService;
    @MockBean
    private BookingRepository bookingRepository;
    @MockBean
    private BookingValidation bookingValidation;
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
    private static final User user = new User();
    private static final LocalDateTime startTime = LocalDateTime.now();
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

        bookingBefore.setId(1L);
        bookingBefore.setStart(startTimeBookingBefore);
        bookingBefore.setEnd(endTimeBookingBefore);
        bookingBefore.setBooker(user);
        bookingBefore.setStatus(BookingStatus.APPROVED);

        bookingDto.setId(2L);
        bookingDto.setStart(startTime);
        bookingDto.setEnd(endTime);
        bookingDto.setBookerId(user.getId());
        bookingDto.setStatus(BookingStatus.REJECTED);
        bookingDto.setItemId(5L);

        item.setId(1L);
        item.setOwner("testItem");
        item.setDescription("testItemDescription");
        item.setAvailable(true);
        item.setOwner("testUser");
        item.setOwnerId(user.getId());
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
    }

    @Test
    void create() {
        Mockito.doNothing()
                .when(userService).searchUser(Mockito.anyLong());
        Mockito.doNothing()
                .when(itemServiceImpl).checkItem(Mockito.anyLong());
        Mockito.doNothing()
                .when(itemServiceImpl).checkItemAvailable(Mockito.anyLong());
        Mockito.doNothing()
                .when(itemServiceImpl).checkItemOwner(Mockito.anyLong(), Mockito.anyLong());
        Mockito
                .when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));
        Mockito
                .when(itemRepository.findById(bookingDto.getItemId()))
                .thenReturn(Optional.of(item));
        Mockito
                .when(bookingRepository.saveAndFlush(booking))
                .thenReturn(booking);

        Booking bookingResult = bookingService.create(bookingDto, user.getId());

        Mockito
                .verify(bookingRepository, Mockito.times(1)).saveAndFlush(Mockito.any(Booking.class));

        Assert.assertEquals(BookingStatus.WAITING,
                bookingResult.getStatus());
        Assert.assertSame(user, bookingResult.getBooker());
        Assert.assertSame(item, bookingResult.getItem());

    }

    @Test
    void approvedOrRejected() {
    }

    @Test
    void getBookingId() {
        Mockito.doNothing()
                .when(userService).searchUser(user.getId());
        Mockito
                .when(bookingRepository.findById(booking.getId()))
                .thenReturn(Optional.ofNullable(booking));

        Booking bookingResult = bookingService.getBookingId(booking.getId(), user.getId());
        Assert.assertSame(booking, bookingResult);
        Assert.assertNull(bookingService.getBookingId(7L, user.getId()));

    }

    @Test
    void searchBooking() {
    }

    @Test
    void dateValidation() {
    }
}