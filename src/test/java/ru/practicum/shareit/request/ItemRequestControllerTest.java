package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
class ItemRequestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ItemRequestController itemRequestController;
//    @Test
//    void createItemRequest() throws Exception{
//        this.mockMvc.perform(get("/"))
//                .andDo(print()).andExpect(status().isOk())
//                .andExpect(content().string(containsString("Hello, World")));
//    }
//
//    @Test
//    void findAllItemRequestOwner() throws Exception {
//        this.mockMvc.perform(get("/requests").header("X-Sharer-User-Id", 99))
//                .andDo(print())
//                .andExpect(status().is4xxClientError()) // ожидаемый код возврата
//                .andExpect(jsonPath());
//    }

    @Test
    void findAllItemRequestUsers() {
    }

    @Test
    void findItemRequest() {
    }
}