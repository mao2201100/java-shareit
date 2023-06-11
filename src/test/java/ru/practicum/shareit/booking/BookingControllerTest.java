package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookingController.class)
class BookingControllerTest {
    @Autowired
    ObjectMapper mapper;
    @MockBean
    BookingService bookingService;
    @Autowired
    private MockMvc mvc;
    private static Item item = new Item();
    private static User user = new User();
    private static BookingDto bookingDto = new BookingDto();
    private static Booking booking1 = new Booking();
    private static Booking booking2 = new Booking();
    private static Booking booking3 = new Booking();
    Collection<Booking> bookingsCollection = List.of(booking1, booking2, booking3);

    @BeforeAll
    static void setup() {
        user.setId(1L);
        user.setName("test1");
        user.setEmail("test1@mail.ru");
        item.setId(1L);
        item.setName("testItem");

        bookingDto.setId(1L);
        bookingDto.setStart(LocalDateTime.of(2023, 06, 03, 10, 00, 00));
        bookingDto.setEnd(LocalDateTime.of(2023, 06, 03, 10, 00, 00));
        bookingDto.setBookerId(user.getId());
        bookingDto.setItemId(1L);
        bookingDto.setStatus(BookingStatus.APPROVED);
        booking3.setId(item.getId());

        booking1.setId(1L);
        booking1.setStart(bookingDto.getStart());
        booking1.setEnd(bookingDto.getEnd());
        booking1.setBooker(user);
        booking1.setStatus(BookingStatus.WAITING);
        booking1.setItem(item);

        booking2.setId(2L);
        booking2.setStart(LocalDateTime.now());
        booking2.setEnd(LocalDateTime.now().plusDays(1));
        booking2.setBooker(user);
        booking2.setStatus(BookingStatus.WAITING);
        booking2.setItem(item);

        booking3.setId(3L);
        booking3.setStart(LocalDateTime.now());
        booking3.setEnd(LocalDateTime.now().plusDays(1));
        booking3.setBooker(user);
        booking3.setStatus(BookingStatus.WAITING);
        booking3.setItem(item);
    }

    @Test
    void create() throws Exception {
        when(bookingService.create(bookingDto, user.getId()))
                .thenReturn(booking1);

        mvc.perform(post("/bookings")
                        .content(mapper.writeValueAsString(bookingDto))
                        .header("X-Sharer-User-Id", user.getId())
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(booking1.getId()), Long.class));
    }

    @Test
    void approvedOrRejected() throws Exception {
        when(bookingService.approvedOrRejected(anyBoolean(), anyLong(), anyLong()))
                .thenReturn(booking1);

        mvc.perform(patch("/bookings/1")
                        .queryParam("approved", "true")
                        .header("X-Sharer-User-Id", user.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(booking1.getId()), Long.class));
    }

    @Test
    void bookingsUser() throws Exception {
        when(bookingService.bookingsUser(anyString(), anyLong(), anyLong(), anyLong()))
                .thenReturn(bookingsCollection);

        mvc.perform(get("/bookings")
                        .param("state", "ALL")
                        .param("from", "0")
                        .param("size", "2")
                        .header("X-Sharer-User-Id", user.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(booking1.getId()), Long.class))
                .andExpect(jsonPath("$[1].id", is(booking2.getId()), Long.class))
                .andExpect(jsonPath("$[2].id", is(booking3.getId()), Long.class));
    }

    @Test
    void bookingsOwner() throws Exception {
        when(bookingService.bookingsOwner(anyString(), anyLong(), anyLong(), anyLong()))
                .thenReturn(bookingsCollection);

        mvc.perform(get("/bookings/owner")
                        .param("state", "ALL")
                        .param("from", "0")
                        .param("size", "2")
                        .header("X-Sharer-User-Id", user.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(booking1.getId()), Long.class))
                .andExpect(jsonPath("$[1].id", is(booking2.getId()), Long.class))
                .andExpect(jsonPath("$[2].id", is(booking3.getId()), Long.class));
    }

    @Test
    void bookingId() throws Exception {
        when(bookingService.getBookingId(anyLong(), anyLong()))
                .thenReturn(booking1);

        mvc.perform(get("/bookings/1")
                        .header("X-Sharer-User-Id", user.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(booking1.getId()), Long.class));
    }
}