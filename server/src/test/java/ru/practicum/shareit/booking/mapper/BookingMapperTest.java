package ru.practicum.shareit.booking.mapper;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.comments.Comments;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.util.List;

class BookingMapperTest {
    private static final BookingMapper mapper = new BookingMapper();
    private static final User user = new User();
    private static final Booking booking = new Booking();
    private static final LocalDateTime startTime = LocalDateTime.now();
    private static final LocalDateTime endTime = LocalDateTime.now().plusHours(1L);


    @BeforeAll
    static void setup() {
        user.setId(1L);
        user.setName("test");
        user.setEmail("jepp@ebrilo.test");

        booking.setId(2L);
        booking.setStart(startTime);
        booking.setEnd(endTime);
        booking.setBooker(user);
        booking.setStatus(BookingStatus.APPROVED);
    }

    @Test
    void toBookingDtoOutMissingItemThrowsException() {
        booking.setItem(null);

        Assert.assertThrows(IllegalArgumentException.class, () -> mapper.toBookingDtoOut(booking));
    }

    @Test
    void toBookingDtoOut() {
        var comment1 = new Comments();
        var comment2 = new Comments();

        var item = new Item();
        item.setId(3L);
        item.setAvailable(true);
        item.setName("name");
        item.setDescription("description");
        item.setOwner("owner");
        item.setComments(List.of(comment1, comment2));

        booking.setItem(item);

        var result = mapper.toBookingDtoOut(booking);

        Assert.assertEquals(1L, result.getBookerId());
        Assert.assertEquals(2L, result.getId());
        Assert.assertEquals(3L, result.getItemId());
        Assert.assertEquals(startTime, result.getStart());
        Assert.assertEquals(endTime, result.getEnd());
    }
}