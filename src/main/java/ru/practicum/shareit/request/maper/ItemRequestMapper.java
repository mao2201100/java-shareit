package ru.practicum.shareit.request.maper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.request.Request;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItemRequestMapper {

    private ItemRepository itemRepository;
    private ItemService itemService;

    public ItemRequestMapper(ItemRepository itemRepository, ItemService itemService) {
        this.itemRepository = itemRepository;
        this.itemService = itemService;
    }

    List<Item> i = new ArrayList<>();

    public ItemRequestDto toItemRequestDto(Request request) {
        List<Item> items = request.getItems();
        return new ItemRequestDto(
                request.getId(),
                request.getDescription(),
                request.getCreated(),
                request.getRequestorId(),
                items != null ? request.getItems() : i
        );
    }
}
