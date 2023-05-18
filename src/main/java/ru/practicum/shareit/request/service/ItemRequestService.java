package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.Request;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.Collection;
import java.util.List;

public interface ItemRequestService {

    ItemRequestDto create(Request request, long userId);

    List<ItemRequestDto> findAllItemRequestUsers(Long from, Long size, long userId);

    List<ItemRequestDto> findAllItemRequestOwner(Long ownerId);

    ItemRequestDto findItemRequest(long requestId, long userId);

}
