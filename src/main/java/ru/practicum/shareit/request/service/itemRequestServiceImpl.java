package ru.practicum.shareit.request.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.request.Request;
import ru.practicum.shareit.request.RequestRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.maper.ItemRequestMapper;
import ru.practicum.shareit.request.validation.ItemRequestValidation;
import ru.practicum.shareit.user.service.UserService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class itemRequestServiceImpl implements ItemRequestService {

    private final ItemRequestValidation itemRequestValidation;
    private final RequestRepository requestRepository;
    private final UserService userService;
    private final ItemRequestMapper itemRequestMapper;
    private final ItemRepository itemRepository;

    @Override
    public ItemRequestDto create(Request request, long userId) {
        itemRequestValidation.itemRequest(request);
        userService.searchUser(userId);
        request.setRequestorId(userId);
        request.setCreated(Timestamp.from(Instant.now()));
        requestRepository.save(request);
        return itemRequestMapper.toItemRequestDto(request);
    }

    @Override
    public Collection<ItemRequestDto> findAllItemRequestUsers(Long from, Long size, long userId) {
        if (from == null && size == null) {
            return requestRepository.findAll().stream().map(itemRequestMapper::toItemRequestDto).collect(Collectors.toList());
        }
        itemRequestValidation.itemRequestIdIsFirstAndSizeIndex(from, size);
        Pageable pageable = PageRequest.of(Math.toIntExact(from), Math.toIntExact(size), Sort.by("created"));
        List<Request> requestList = requestRepository.findByRequestorIdNot(userId, pageable);
        Collection<ItemRequestDto> requestDtoCollection = requestList.stream().map(itemRequestMapper::toItemRequestDto).collect(Collectors.toList());
        return requestDtoCollection;
    }

    @Override
    public List<ItemRequestDto> findAllItemRequestOwner(Long ownerId) {
        userService.searchUser(ownerId);
        return requestRepository.ownerRequest(ownerId).stream()
                .map(itemRequestMapper::toItemRequestDto)
                .peek(x -> {
                    x.setItems(itemRepository.itemsForRequestId(ownerId));
                })
                .collect(Collectors.toList());
    }

    @Override
    public ItemRequestDto findItemRequest(long requestId, long userId) {
        userService.searchUser(userId);
        itemRequestValidation.itemRequestIdIsEmpty(requestRepository.existsById(requestId));
        return itemRequestMapper.toItemRequestDto(requestRepository.findById(requestId).orElseThrow());
    }
}
