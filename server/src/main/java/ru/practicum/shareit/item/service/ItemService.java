package ru.practicum.shareit.item.service;

import ru.practicum.shareit.comments.Comments;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemService {
    ItemDto findItemById(Long itemId, Long ownerId);

    Collection<ItemDto> itemAllOwnerId(Long ownerId);

    Collection<ItemDto> itemSearch(String text, long ownerId);

    ItemDto create(Item item, long idUser);

    ItemDto update(Item item, Long itemId, Long ownerId);

    CommentDto createComments(Comments comments, long authorId, long itemId);
}
