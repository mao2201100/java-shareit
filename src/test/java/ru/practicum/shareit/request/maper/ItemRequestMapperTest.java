package ru.practicum.shareit.request.maper;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.Request;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.sql.Timestamp;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class ItemRequestMapperTest {
    @Autowired
    ItemRequestMapper itemRequestMapper;
    private static final Request request = new Request();
    private static final Item item = new Item();
    static List<Item> items;

    @BeforeAll
    static void setup() {
        request.setId(1);
        request.setDescription("test");
        request.setCreated(Timestamp.valueOf("2023-05-22 23:00:29.0"));
        request.setRequestorId(1L);

        item.setId(1L);
        item.setName("test_item");
        item.setDescription("test_description");
    }

    @Test
    void toItemRequestDtoEmptyItemList() {
        items = List.of();
        request.setItems(items);
        ItemRequestDto itemRequestDto = itemRequestMapper.toItemRequestDto(request);
        Assert.assertEquals(request.getId(), itemRequestDto.getId());
        Assert.assertEquals(request.getDescription(), itemRequestDto.getDescription());
        Assert.assertEquals(request.getCreated(), itemRequestDto.getCreated());
        Assert.assertEquals(request.getRequestorId(), itemRequestDto.getRequestorId());
        Assert.assertArrayEquals(request.getItems().toArray(), itemRequestDto.getItems().toArray());
    }

    @Test
    void toItemRequestDtoFullItemList() {
        items = List.of(item);
        request.setItems(items);
        ItemRequestDto itemRequestDto = itemRequestMapper.toItemRequestDto(request);
        Assert.assertEquals(request.getId(), itemRequestDto.getId());
        Assert.assertEquals(request.getDescription(), itemRequestDto.getDescription());
        Assert.assertEquals(request.getCreated(), itemRequestDto.getCreated());
        Assert.assertEquals(request.getRequestorId(), itemRequestDto.getRequestorId());
        Assert.assertArrayEquals(items.toArray(), itemRequestDto.getItems().toArray());
    }
}