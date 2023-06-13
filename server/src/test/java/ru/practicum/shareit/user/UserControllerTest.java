package ru.practicum.shareit.user;

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
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
    @Autowired
    ObjectMapper mapper;
    @MockBean
    UserService userService;
    @Autowired
    private MockMvc mvc;

    private static User user = new User();
    private UserDto user1 = new UserDto(1L, "test1", "test1@mail.ru");
    private UserDto user2 = new UserDto(2L, "test2", "test2@mail.ru");
    private UserDto user3 = new UserDto(3L, "test3", "test3@mail.ru");
    private final List<UserDto> userDtoList = List.of(user1, user2, user3);

    @BeforeAll
    static void setup() {
        user.setName("test1");
        user.setEmail("test1@mail.ru");
    }

    @Test
    void findById() throws Exception {
        when(userService.findUserById(Mockito.anyLong()))
                .thenReturn(user1);

        mvc.perform(get("/users/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user1.getId()), Long.class))
                .andExpect((jsonPath("$.name", is(user1.getName()))))
                .andExpect(jsonPath("$.email", is(user1.getEmail())));
    }

    @Test
    void findByIdException() throws Exception {
        mvc.perform(get("/users/k"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void readAll() throws Exception {
        when(userService.getUsers())
                .thenReturn(userDtoList);
        mvc.perform(get("/users"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(userDtoList.get(0).getId()), Long.class))
                .andExpect(jsonPath("$[0].name", is(userDtoList.get(0).getName())))
                .andExpect(jsonPath("$[0].email", is(userDtoList.get(0).getEmail())))
                .andExpect(jsonPath("$[1].id", is(userDtoList.get(1).getId()), Long.class))
                .andExpect(jsonPath("$[1].name", is(userDtoList.get(1).getName())))
                .andExpect(jsonPath("$[1].email", is(userDtoList.get(1).getEmail())))
                .andExpect(jsonPath("$[2].id", is(userDtoList.get(2).getId()), Long.class))
                .andExpect(jsonPath("$[2].name", is(userDtoList.get(2).getName())))
                .andExpect(jsonPath("$[2].email", is(userDtoList.get(2).getEmail())));

    }

    @Test
    void readAllException() throws Exception {
        mvc.perform(get("/users/k"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void create() throws Exception {
        when(userService.create(any()))
                .thenReturn(user1);

        mvc.perform(post("/users")
                        .content(mapper.writeValueAsString(user1))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user1.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(user1.getName())))
                .andExpect(jsonPath("$.email", is(user1.getEmail())));
    }

    @Test
    void createException() throws Exception {
        mvc.perform(post("/users/k")
                        .content(mapper.writeValueAsString(user1))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void update() throws Exception {
        UserDto userUpdateDto = new UserDto(1L, "test1Update", "test1Update@mail.ru");

        when(userService.update(anyLong(), any(User.class)))
                .thenReturn(userUpdateDto);

        mvc.perform(patch("/users/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(userUpdateDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userUpdateDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(userUpdateDto.getName())))
                .andExpect(jsonPath("$.email", is(userUpdateDto.getEmail())));
    }

    @Test
    void updateEXception() throws Exception {
        UserDto userUpdateDto = new UserDto(1L, "test1Update", "test1Update@mail.ru");
        mvc.perform(patch("/users/k")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(userUpdateDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void deleteFriends() throws Exception {
        Long idUser = 1L;
        doNothing()
                .when(userService).deleteUser(anyLong());

        mvc.perform(delete("/users/1")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(idUser))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteFriendsException() throws Exception {
        Long idUser = 1L;
        doNothing()
                .when(userService).deleteUser(anyLong());

        mvc.perform(delete("/users/k")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(idUser))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}