package ru.practicum.shareit.request;

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
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;
import ru.practicum.shareit.user.User;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemRequestController.class)
class ItemRequestControllerTest {
    @Autowired
    ObjectMapper mapper;
    @MockBean
    ItemRequestService itemRequestService;
    @Autowired
    private MockMvc mvc;
    private static List<Item> itemList = List.of();
    private static User user = new User();
    private static Request request = new Request();
    private ItemRequestDto itemRequestDto1 = new ItemRequestDto(1L, "testDescription",
            Timestamp.from(Instant.now()), 1L, itemList);
    private ItemRequestDto itemRequestDto2 = new ItemRequestDto(2L, "testDescription",
            Timestamp.from(Instant.now()), 1L, itemList);
    private ItemRequestDto itemRequestDto3 = new ItemRequestDto(3L, "testDescription",
            Timestamp.from(Instant.now()), 1L, itemList);

    private List<ItemRequestDto> itemRequestDtosList = List.of(itemRequestDto1, itemRequestDto2, itemRequestDto3);


    @BeforeAll
    static void setup() {
        request.setId(1L);
        request.setDescription("testDescription");
        request.setCreated(Timestamp.from(Instant.now()));
        request.setRequestorId(1L);
        request.setItems(itemList);

        user.setId(1L);
        user.setName("test1");
        user.setEmail("test1@mail.ru");
    }

    @Test
    void createItemRequest() throws Exception {
        when(itemRequestService.create(Mockito.any(Request.class), anyLong()))
                .thenReturn(itemRequestDto1);

        mvc.perform(post("/requests")
                        .header("X-Sharer-User-Id", user.getId())
                        .content(mapper.writeValueAsString(request))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemRequestDto1.getId()), Long.class))
                .andExpect(jsonPath("$.description", is(itemRequestDto1.getDescription())))
                .andExpect(jsonPath("$.requestorId", is(itemRequestDto1.getRequestorId()), Long.class))
                .andExpect((jsonPath("$.items", is(itemRequestDto1.getItems()))));
    }

    @Test
    void findAllItemRequestOwner() throws Exception {
        when(itemRequestService.findAllItemRequestOwner(Mockito.anyLong()))
                .thenReturn(itemRequestDtosList);

        mvc.perform(get("/requests")
                        .header("X-Sharer-User-Id", user.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(itemRequestDto1.getId()), Long.class))
                .andExpect(jsonPath("$[0].description", is(itemRequestDto1.getDescription())))
                .andExpect(jsonPath("$[0].requestorId", is(itemRequestDto1.getRequestorId()), Long.class))
                .andExpect((jsonPath("$[0].items", is(itemRequestDto1.getItems()))));
    }

    @Test
    void findAllItemRequestUsers() throws Exception {
        when(itemRequestService.findAllItemRequestUsers(null, null, user.getId()))
                .thenReturn(itemRequestDtosList);

        mvc.perform(get("/requests/all")
                        .header("X-Sharer-User-Id", user.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(itemRequestDto1.getId()), Long.class))
                .andExpect(jsonPath("$[0].description", is(itemRequestDto1.getDescription())))
                .andExpect(jsonPath("$[0].requestorId", is(itemRequestDto1.getRequestorId()), Long.class))
                .andExpect((jsonPath("$[0].items", is(itemRequestDto1.getItems()))))
                .andExpect(jsonPath("$[1].id", is(itemRequestDto2.getId()), Long.class))
                .andExpect(jsonPath("$[1].description", is(itemRequestDto2.getDescription())))
                .andExpect(jsonPath("$[1].requestorId", is(itemRequestDto2.getRequestorId()), Long.class))
                .andExpect((jsonPath("$[1].items", is(itemRequestDto2.getItems()))))
                .andExpect(jsonPath("$[2].id", is(itemRequestDto3.getId()), Long.class))
                .andExpect(jsonPath("$[2].description", is(itemRequestDto3.getDescription())))
                .andExpect(jsonPath("$[2].requestorId", is(itemRequestDto3.getRequestorId()), Long.class))
                .andExpect((jsonPath("$[2].items", is(itemRequestDto3.getItems()))));
    }

    @Test
    void findAllItemRequestUsersSize() throws Exception {
        when(itemRequestService.findAllItemRequestUsers(0L, 2L, user.getId()))
                .thenReturn(itemRequestDtosList.subList(0,2));

        mvc.perform(get("/requests/all")
                        .param("from", "0")
                        .param("size", "2")
                        .header("X-Sharer-User-Id", user.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(itemRequestDto1.getId()), Long.class))
                .andExpect(jsonPath("$[0].description", is(itemRequestDto1.getDescription())))
                .andExpect(jsonPath("$[0].requestorId", is(itemRequestDto1.getRequestorId()), Long.class))
                .andExpect((jsonPath("$[0].items", is(itemRequestDto1.getItems()))))
                .andExpect(jsonPath("$[1].id", is(itemRequestDto2.getId()), Long.class))
                .andExpect(jsonPath("$[1].description", is(itemRequestDto2.getDescription())))
                .andExpect(jsonPath("$[1].requestorId", is(itemRequestDto2.getRequestorId()), Long.class))
                .andExpect((jsonPath("$[1].items", is(itemRequestDto2.getItems()))));
    }

    @Test
    void findItemRequest() throws Exception {
        when(itemRequestService.findItemRequest(request.getId(), user.getId()))
                .thenReturn(itemRequestDto1);

        mvc.perform(get("/requests/1")
                        .param("requestId", "1")
                        .header("X-Sharer-User-Id", user.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemRequestDto1.getId()), Long.class))
                .andExpect(jsonPath("$.description", is(itemRequestDto1.getDescription())))
                .andExpect(jsonPath("$.requestorId", is(itemRequestDto1.getRequestorId()), Long.class))
                .andExpect((jsonPath("$.items", is(itemRequestDto1.getItems()))));
    }
}