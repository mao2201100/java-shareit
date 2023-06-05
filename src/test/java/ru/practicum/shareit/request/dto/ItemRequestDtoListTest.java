package ru.practicum.shareit.request.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.request.Request;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemRequestDtoListTest {

    private static Request request = new Request();
    List<Request> requestList = List.of(request);

    ItemRequestDtoList itemRequestDtoList = new ItemRequestDtoList(requestList);


    @BeforeAll
    static void setup() {
        request.setId(1L);
    }

    @Test
    void getRequest() {
        assertEquals(1L, itemRequestDtoList.getRequest().get(0).getId());
    }
}