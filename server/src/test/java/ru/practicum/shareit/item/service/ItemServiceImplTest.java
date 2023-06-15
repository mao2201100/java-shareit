package ru.practicum.shareit.item.service;

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
import ru.practicum.shareit.comments.CommentRepository;
import ru.practicum.shareit.comments.Comments;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.sql.Timestamp;
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
    private ItemService itemService;
    @Autowired
    private ItemServiceImpl itemServiceImpl;
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
    private static final LocalDateTime startTime = LocalDateTime.now().minusDays(1);
    private static final LocalDateTime endTime = LocalDateTime.now().minusHours(1L);
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
        booking.setStatus(BookingStatus.APPROVED);
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

        ItemDto itemDtoRes = itemService.findItemById(item.getId(), user.getId());

        assertEquals(1L, itemDtoRes.getId());
        assertEquals("testItem", itemDtoRes.getName());
        assertEquals(1L, itemDtoRes.getOwnerId());
        assertEquals("testDescription", itemDtoRes.getDescription());
        assertEquals(true, itemDtoRes.getAvailable());
        assertArrayEquals(commentsList.toArray(), itemDtoRes.getComments().toArray());
    }

    @Test
    void itemAllOwnerId() {
        Mockito
                .when(itemRepository.fetchItemByOwnerId(Mockito.anyLong()))
                .thenReturn(itemDtoCollection);

        List<ItemDto> listResult = itemService.itemAllOwnerId(user.getId()).stream().collect(Collectors.toList());

        assertEquals(1L, listResult.get(0).getId());
        assertEquals("testItem", listResult.get(0).getName());
        assertEquals(1L, listResult.get(0).getOwnerId());
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
        assertEquals(1L, listResult.get(0).getOwnerId());
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

        assertEquals(1L, itemDtoResult.getId());
        assertEquals("testItem", itemDtoResult.getName());
        assertEquals(1L, itemDtoResult.getOwnerId());
        assertEquals("testDescription", itemDtoResult.getDescription());
        assertEquals(true, itemDtoResult.getAvailable());
        assertArrayEquals(commentsList.toArray(), itemDtoResult.getComments().toArray());
    }

    @Test
    void update() {
        Item itemUpdate = new Item();
        itemUpdate.setId(1L);
        itemUpdate.setName("testItemUpdate");
        itemUpdate.setOwnerId(1L);
        itemUpdate.setDescription("testDescriptionUpdate");
        itemUpdate.setAvailable(true);
        itemUpdate.setComments(commentsList);

        Item item2 = new Item();
        item2.setId(2L);
        item2.setName("testItem2");
        item2.setOwnerId(1L);
        item2.setDescription("testDescription2");
        item2.setAvailable(true);
        item2.setComments(commentsList);

        Mockito
                .when(itemRepository.getById(Mockito.anyLong()))
                .thenReturn(item2);
        Mockito
                .when(itemRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(item2));
        Mockito
                .when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(user));
        Mockito
                .when(itemRepository.save(Mockito.any(Item.class)))
                .thenReturn(item2);

        ItemDto itemDtoResult = itemService.update(itemUpdate, item2.getId(), user.getId());

        assertEquals(2L, itemDtoResult.getId());
        assertEquals("testItemUpdate", itemDtoResult.getName());
        assertEquals(1L, itemDtoResult.getOwnerId());
        assertEquals("testDescriptionUpdate", itemDtoResult.getDescription());
        assertEquals(true, itemDtoResult.getAvailable());
        assertArrayEquals(commentsList.toArray(), itemDtoResult.getComments().toArray());
    }

    @Test
    void createComments() {
        Comments comments = new Comments();
        comments.setId(1L);
        comments.setText("testText");
        comments.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        comments.setAuthorId(2L);
        comments.setItemId(1L);

        Mockito
                .when(itemRepository.getById(Mockito.anyLong()))
                .thenReturn(item);

        Mockito
                .when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(user));

        Mockito
                .when(commentRepository.saveAndFlush(Mockito.any(Comments.class)))
                .thenReturn(comments);

        List<Booking> bookingsList = List.of(booking);

        Mockito
                .when(bookingRepository.commentsCheck(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(bookingsList);
        Mockito
                .when(commentRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(comments));

        CommentDto commentDto = itemService.createComments(comments, 2L, item.getId());

        assertEquals(1L, commentDto.getId());
        assertEquals("testText", commentDto.getText());
    }

    @Test
    void createCommentsEmptyText() {
        Comments comments = new Comments();
        comments.setId(1L);
        comments.setText("");
        comments.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        comments.setAuthorId(2L);
        comments.setItemId(1L);

        Mockito
                .when(itemRepository.getById(Mockito.anyLong()))
                .thenReturn(item);

        Mockito
                .when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(user));

        Assert.assertThrows(ValidationException.class, () -> itemService.createComments(comments, 2L, item.getId()));
        try {
            itemService.createComments(comments, 2L, item.getId());
        } catch (Exception e) {
            Assert.assertEquals("Комментарий не может быть пустым",
                    e.getMessage());
        }
    }


    @Test
    void checkItem() {
        Item item1 = null;
        Mockito
                .when(itemRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(item1));

        Assert.assertThrows(NotFoundException.class, () -> itemServiceImpl.checkItem(item.getId()));
        try {
            itemServiceImpl.checkItem(item.getId());
        } catch (Exception e) {
            Assert.assertEquals("вещь не найдена",
                    e.getMessage());
        }
    }

    @Test
    void checkItemAvailable() {
        item.setAvailable(false);
        Mockito
                .when(itemRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(item));

        Assert.assertThrows(ValidationException.class, () -> itemServiceImpl.checkItemAvailable(item.getId()));
        try {
            itemServiceImpl.checkItem(item.getId());
        } catch (Exception e) {
            Assert.assertEquals("Вещь недоступна для бронирования",
                    e.getMessage());
        }
    }

    @Test
    void checkItemOwner() {
        Mockito
                .when(itemRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(item));

        Assert.assertThrows(NotFoundException.class, () -> itemServiceImpl.checkItemOwner(item.getId(), 1L));
        try {
            itemServiceImpl.checkItem(item.getId());
        } catch (Exception e) {
            Assert.assertEquals("",
                    e.getMessage());
        }
    }
}