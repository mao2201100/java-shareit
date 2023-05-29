package ru.practicum.shareit.item.maper;

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
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
class ItemMapperTest {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private BookingMapper bookingMapper;
    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private CommentsMapper commentsMapper;
    private static Item item = new Item();
    private static Booking booking = new Booking();
    private static final LocalDateTime startTime = LocalDateTime.now().plusMinutes(3);
    private static final LocalDateTime endTime = LocalDateTime.now().plusHours(1L);
    private static final User user = new User();

    @BeforeAll
    static void setup() {
        item.setId(1L);
        item.setOwner("testUser");
        item.setDescription("testItemDescription");
        item.setAvailable(true);
        item.setOwnerId(1L);

        booking.setId(2L);
        booking.setStart(startTime);
        booking.setEnd(endTime);
        booking.setBooker(user);
        booking.setStatus(BookingStatus.REJECTED);
        booking.setItem(item);

        user.setId(1L);
        user.setName("testUser");
        user.setEmail("jepp@ebrilo.test");
    }

    @Test
    void toItemDto() {
        List<Booking> lastBookings = List.of(booking);
        Mockito
                .when(bookingRepository.fetchLastBookerByItemForAll(item.getId(), 1L))
                .thenReturn(lastBookings);

        ItemDto itemDto = itemMapper.toItemDto(item, 1L);

        Assert.assertEquals(item.getId(), itemDto.getId());
        Assert.assertEquals(item.getName(), itemDto.getName());
        Assert.assertEquals(item.getDescription(), itemDto.getDescription());
        Assert.assertEquals(item.getAvailable(), itemDto.getAvailable());
        Assert.assertEquals(item.getComments(), itemDto.getOwner());
        Assert.assertNull(itemDto.getNextBooking());
        Assert.assertNull(itemDto.getComments());
    }

}