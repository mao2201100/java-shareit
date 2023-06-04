package ru.practicum.shareit.item.service;

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
import ru.practicum.shareit.comments.CommentRepository;
import ru.practicum.shareit.comments.Comments;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.validation.ItemValidation;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
class ItemServiceImplTest {
    @Autowired
    ItemService itemService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private ItemValidation itemValidation;
    @MockBean
    private ItemRepository itemRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private CommentRepository commentRepository;
    @MockBean
    private BookingRepository bookingRepository;
    private static Item item = new Item();
    private static Booking booking = new Booking();
    private static final User user = new User();
    private static final LocalDateTime startTime = LocalDateTime.now().plusMinutes(3);
    private static final LocalDateTime endTime = LocalDateTime.now().plusHours(1L);
    private static List<Comments> commentsList = List.of();
    private static List<CommentDto> commentsDtoList = List.of();
    private static ItemDto itemDto1 = new ItemDto(1L, "testItem", "testDescription", true,
            1L, 1L, null, null, commentsDtoList);
    private static ItemDto itemDto2 = new ItemDto(2L, "testItem", "testDescription", true,
            1L, 2L, null, null, commentsDtoList);
    private static ItemDto itemDto3 = new ItemDto(3L, "testItem", "testDescription", true,
            1L, 3L, null, null, commentsDtoList);
    List<Item> itemDtoCollection = List.of(item);

    @BeforeAll
    static void setup() {
        item.setId(1L);
        item.setName("testItem");
        item.setOwnerId(1L);
        item.setDescription("testDescription");
        item.setAvailable(true);
        item.setComments(commentsList);

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
    void findItemById() {
        Mockito
                .when(itemRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(item));
        Mockito
                .when(itemRepository.getById(Mockito.anyLong()))
                .thenReturn(item);

        ItemDto itemDtoResult = itemService.findItemById(item.getId(), user.getId());

        assertEquals(1l, itemDtoResult.getId());
        assertEquals("testItem", itemDtoResult.getName());
        assertEquals(1l, itemDtoResult.getOwnerId());
        assertEquals("testDescription", itemDtoResult.getDescription());
        assertEquals(true, itemDtoResult.getAvailable());
        assertArrayEquals(commentsList.toArray(), itemDtoResult.getComments().toArray());
    }

    @Test
    void itemAllOwnerId() {
        Mockito
                .when(itemRepository.fetchItemByOwnerId(Mockito.anyLong()))
                .thenReturn(itemDtoCollection);

        List<ItemDto> listResult = itemService.itemAllOwnerId(user.getId()).stream().collect(Collectors.toList());

        assertEquals(1L, listResult.get(0).getId());
        assertEquals("testItem", listResult.get(0).getName());
        assertEquals(1l, listResult.get(0).getOwnerId());
        assertEquals("testDescription", listResult.get(0).getDescription());
        assertEquals(true, listResult.get(0).getAvailable());
        assertArrayEquals(commentsList.toArray(), listResult.get(0).getComments().toArray());
    }

    @Test
    void itemSearchTextIsEmpty() {
        assertArrayEquals(List.of().toArray(), itemService.itemSearch("", user.getId()).toArray());
    }

    @Test
    void itemSearch() {
        Mockito
                .when(itemRepository.search(Mockito.anyString()))
                .thenReturn(itemDtoCollection);

        List<ItemDto> listResult = itemService.itemSearch("test", user.getId()).stream().collect(Collectors.toList());
        assertEquals(1L, listResult.get(0).getId());
        assertEquals("testItem", listResult.get(0).getName());
        assertEquals(1l, listResult.get(0).getOwnerId());
        assertEquals("testDescription", listResult.get(0).getDescription());
        assertEquals(true, listResult.get(0).getAvailable());
        assertArrayEquals(commentsList.toArray(), listResult.get(0).getComments().toArray());
    }

    @Test
    void create() {
        Mockito
                .when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(user));
        Mockito
                .when(userRepository.getReferenceById(Mockito.anyLong()))
                .thenReturn(user);
        Mockito
                .when(userRepository.getById(Mockito.anyLong()))
                .thenReturn(user);
        Mockito
                .when(itemRepository.saveAndFlush(Mockito.any(Item.class)))
                .thenReturn(item);
        Mockito
                .when(itemRepository.getById(Mockito.anyLong()))
                .thenReturn(item);

        Mockito
                .when(itemRepository.getReferenceById(Mockito.anyLong()))
                .thenReturn(item);

        ItemDto itemDtoResult = itemService.create(item, user.getId());

        assertEquals(1l, itemDtoResult.getId());
        assertEquals("testItem", itemDtoResult.getName());
        assertEquals(1l, itemDtoResult.getOwnerId());
        assertEquals("testDescription", itemDtoResult.getDescription());
        assertEquals(true, itemDtoResult.getAvailable());
        assertArrayEquals(commentsList.toArray(), itemDtoResult.getComments().toArray());
    }

    @Test
    void update() {
    }

    @Test
    void createComments() {
    }

    @Test
    void checkItem() {
    }

    @Test
    void checkItemAvailable() {
    }

    @Test
    void checkItemOwner() {
    }
}