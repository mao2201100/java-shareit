package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemService {
    ItemDto findItemById(Long itemId);

    Collection<ItemDto> itemAllOwnerId(Long ownerId);

    Collection<ItemDto> itemSearch(String text);

    ItemDto create(Item item, long idUser);

    ItemDto update(Item item, Long itemId, Long ownerId);
}
