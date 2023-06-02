package ru.practicum.shareit.item;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import ru.practicum.shareit.comments.Comments;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.User;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemController.class)
class ItemControllerTest {
    @Autowired
    ObjectMapper mapper;
    @MockBean
    ItemService itemService;
    @Autowired
    private MockMvc mvc;

    private static User user = new User();
    private static List<CommentDto> commentList = List.of();
    private static List<Comments> comments = List.of();
    private static Item item = new Item();
    private ItemDto itemDto1 = new ItemDto(1L, "itemDtoTest1", "itemDtoTestDescription1"
            , true, 1L, 1L, null, null, commentList);
    private ItemDto itemDto2 = new ItemDto(2L, "itemDtoTest2", "itemDtoTestDescription2"
            , true, 1L, 2L, null, null, commentList);
    private ItemDto itemDto3 = new ItemDto(3L, "itemDtoTest2", "itemDtoTestDescription2"
            , true, 1L, 3L, null, null, commentList);
    private final List<ItemDto> itemDtoList = List.of(itemDto1, itemDto2, itemDto3);

    @BeforeAll
    static void setup() {
        user.setId(1L);
        user.setName("test1");
        user.setEmail("test1@mail.ru");

        item.setId(1L);
        item.setName("itemTest1");
        item.setDescription("itemTestDescription1");
        item.setAvailable(true);
        item.setOwnerId(1L);
        item.setRequestId(1L);
        item.setComments(comments);

    }

    @Test
    void findById() throws Exception {
        when(itemService.findItemById(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(itemDto1);

        mvc.perform(get("/items/1")
                        .header("X-Sharer-User-Id", user.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDto1.getId()), Long.class))
                .andExpect((jsonPath("$.name", is(itemDto1.getName()))))
                .andExpect(jsonPath("$.description", is(itemDto1.getDescription())))
                .andExpect(jsonPath("$.available", is(itemDto1.getAvailable())))
                .andExpect((jsonPath("$.ownerId", is(itemDto1.getOwnerId()), Long.class)))
                .andExpect(jsonPath("$.requestId", is(itemDto1.getRequestId()), Long.class))
                .andExpect(jsonPath("$.lastBooking", is(itemDto1.getLastBooking())))
                .andExpect((jsonPath("$.nextBooking", is(itemDto1.getNextBooking()))))
                .andExpect(jsonPath("$.comments", is(itemDto1.getComments())));

    }

    @Test
    void itemAllOwnerId() throws Exception {
        when(itemService.itemAllOwnerId(Mockito.anyLong()))
                .thenReturn(itemDtoList);

        mvc.perform(get("/items")
                        .header("X-Sharer-User-Id", user.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(itemDtoList.get(0).getId()), Long.class))
                .andExpect((jsonPath("$[0].name", is(itemDtoList.get(0).getName()))))
                .andExpect(jsonPath("$[0].description", is(itemDtoList.get(0).getDescription())))
                .andExpect(jsonPath("$[0].available", is(itemDtoList.get(0).getAvailable())))
                .andExpect((jsonPath("$[0].ownerId", is(itemDtoList.get(0).getOwnerId()), Long.class)))
                .andExpect(jsonPath("$[0].requestId", is(itemDtoList.get(0).getRequestId()), Long.class))
                .andExpect(jsonPath("$[0].lastBooking", is(itemDtoList.get(0).getLastBooking())))
                .andExpect((jsonPath("$[0].nextBooking", is(itemDtoList.get(0).getNextBooking()))))
                .andExpect(jsonPath("$[0].comments", is(itemDtoList.get(0).getComments())));
    }

    @Test
    void itemSearch() throws Exception {
        when(itemService.itemSearch(Mockito.anyString(), anyLong()))
                .thenReturn(itemDtoList);

        mvc.perform(get("/items/search")
                        .param("text", "itemDtoTest")
                        .header("X-Sharer-User-Id", user.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(itemDtoList.get(0).getId()), Long.class))
                .andExpect((jsonPath("$[0].name", is(itemDtoList.get(0).getName()))))
                .andExpect(jsonPath("$[0].description", is(itemDtoList.get(0).getDescription())))
                .andExpect(jsonPath("$[0].available", is(itemDtoList.get(0).getAvailable())))
                .andExpect((jsonPath("$[0].ownerId", is(itemDtoList.get(0).getOwnerId()), Long.class)))
                .andExpect(jsonPath("$[0].requestId", is(itemDtoList.get(0).getRequestId()), Long.class))
                .andExpect(jsonPath("$[0].lastBooking", is(itemDtoList.get(0).getLastBooking())))
                .andExpect((jsonPath("$[0].nextBooking", is(itemDtoList.get(0).getNextBooking()))))
                .andExpect(jsonPath("$[0].comments", is(itemDtoList.get(0).getComments())));
    }

    @Test
    void create() throws Exception {
        when(itemService.create(any(Item.class), anyLong()))
                .thenReturn(itemDto1);

        mvc.perform(post("/items")
                        .header("X-Sharer-User-Id", user.getId())
                        .content(mapper.writeValueAsString(item))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDto1.getId()), Long.class))
                .andExpect((jsonPath("$.name", is(itemDto1.getName()))))
                .andExpect(jsonPath("$.description", is(itemDto1.getDescription())))
                .andExpect(jsonPath("$.available", is(itemDto1.getAvailable())))
                .andExpect((jsonPath("$.ownerId", is(itemDto1.getOwnerId()), Long.class)))
                .andExpect(jsonPath("$.requestId", is(itemDto1.getRequestId()), Long.class))
                .andExpect(jsonPath("$.lastBooking", is(itemDto1.getLastBooking())))
                .andExpect((jsonPath("$.nextBooking", is(itemDto1.getNextBooking()))))
                .andExpect(jsonPath("$.comments", is(itemDto1.getComments())));
    }

    @Test
    void createComments() throws Exception {
        Comments comments = new Comments();
        comments.setItemId(1L);
        comments.setText("testComment");
        comments.setItemId(1L);
        comments.setAuthorId(2L);
        comments.setCreated(Timestamp.from(Instant.now()));
        CommentDto commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setText("testComment");
        commentDto.setItemId(1L);
        commentDto.setAuthorId(2L);
        commentDto.setAuthorName("testAuthor");

        commentDto.setCreated(Timestamp.from(Instant.now()));

        when(itemService.createComments(any(Comments.class), anyLong(), anyLong()))
                .thenReturn(commentDto);

        mvc.perform(post("/items/1/comment")
                        .header("X-Sharer-User-Id", user.getId())
                        .content(mapper.writeValueAsString(comments))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(commentDto.getId()), Long.class))
                .andExpect((jsonPath("$.text", is(commentDto.getText()))))
                .andExpect(jsonPath("$.itemId", is(commentDto.getItemId()), Long.class))
                .andExpect(jsonPath("$.authorId", is(commentDto.getAuthorId()), Long.class))
                .andExpect(jsonPath("$.authorName", is(commentDto.getAuthorName())));
    }

    @Test
    void update() throws Exception {
        when(itemService.update(any(Item.class), anyLong(), anyLong()))
                .thenReturn(itemDto1);

        mvc.perform(patch("/items/1")
                        .header("X-Sharer-User-Id", user.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(item))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDto1.getId()), Long.class))
                .andExpect((jsonPath("$.name", is(itemDto1.getName()))))
                .andExpect(jsonPath("$.description", is(itemDto1.getDescription())))
                .andExpect(jsonPath("$.available", is(itemDto1.getAvailable())))
                .andExpect((jsonPath("$.ownerId", is(itemDto1.getOwnerId()), Long.class)))
                .andExpect(jsonPath("$.requestId", is(itemDto1.getRequestId()), Long.class))
                .andExpect(jsonPath("$.lastBooking", is(itemDto1.getLastBooking())))
                .andExpect((jsonPath("$.nextBooking", is(itemDto1.getNextBooking()))))
                .andExpect(jsonPath("$.comments", is(itemDto1.getComments())));
    }
}