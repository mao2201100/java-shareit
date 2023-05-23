package ru.practicum.shareit.request.maper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.Request;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

@Component
public class ItemRequestMapper {

    List<Item> i = List.of();

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
