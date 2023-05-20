package ru.practicum.shareit.request.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.Request;
import ru.practicum.shareit.request.RequestRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.maper.ItemRequestMapper;
import ru.practicum.shareit.request.validation.ItemRequestValidation;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.service.UserService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
class ItemRequestServiceImplTest {
    @Autowired
    private ItemRequestService itemRequestServiceImpl;
    @SpyBean
    private ItemRequestValidation itemRequestValidation;
    @SpyBean
    private UserService userService;
    @SpyBean
    private ItemRepository itemRepository;
    @MockBean
    private RequestRepository requestRepository;
    @MockBean
    private ItemRequestMapper itemRequestMapper;

    @Test
    void create() {
        Request request = new Request();
        request.setDescription("test");
        request.setId(1);
        User user = new User();
        user.setId(1);
        user.setName("test");
        user.setEmail("test@mail.ru");
        ItemRequestDto itemRequestDto = new ItemRequestDto(request.getId(), " ", request.getCreated(),
                1L, Collections.EMPTY_LIST);

        Mockito.doNothing()
                .when(itemRequestValidation).itemRequest(request);

        Mockito.doNothing()
                .when(userService).searchUser(user.getId());

        Mockito
                .when(itemRequestMapper.toItemRequestDto(request))
                .thenReturn(itemRequestDto);
        ItemRequestDto isItemRequestDtoCreated = itemRequestServiceImpl.create(request, user.getId());
        boolean create = isItemRequestDtoCreated != null ? true : false;
        Assertions.assertTrue(create);

    }

    @Test
    void findAllItemRequestUsers() {
        Long size = null;
        Long from = null;
        Request request = new Request();
        request.setDescription("test");
        request.setId(1);
        User user = new User();
        user.setId(1);
        user.setName("test");
        user.setEmail("test@mail.ru");
        request.setRequestorId(user.getId());
        request.setCreated(Timestamp.from(Instant.now()));
        List<Item> items = List.of();
        ItemRequestDto itemRequestDto = new ItemRequestDto(request.getId(), request.getDescription(), request.getCreated(),
                1L, items);
        List<Request> list = List.of(request);

        Mockito
                .when(itemRequestMapper.toItemRequestDto(request))
                .thenReturn(itemRequestDto);

        Mockito
                .when(requestRepository.findAll())
                .thenReturn(list);

        Collection<ItemRequestDto> itemRequestDtoCollection = itemRequestServiceImpl.findAllItemRequestUsers(from, size, user.getId());
        boolean findAllRequest = itemRequestDtoCollection != null ? true : false;
        Assertions.assertTrue(findAllRequest);
    }

    @Test
    public void findAllItemRequestOwner() {
        Request request = new Request();
        request.setDescription("test");
        request.setId(1);
        request.setRequestorId(1L);
        User owner = new User();
        owner.setId(1);
        owner.setName("test");
        owner.setEmail("test@mail.ru");
        request.setRequestorId(owner.getId());
        request.setCreated(Timestamp.from(Instant.now()));
        List<Item> items = List.of();
        ItemRequestDto itemRequestDto = new ItemRequestDto(request.getId(), request.getDescription(), request.getCreated(),
                1L, items);
        List<Request> listRequest = List.of(request);
        List<ItemRequestDto> list = List.of(itemRequestDto);

        Mockito.doNothing()
                .when(userService).searchUser(owner.getId());

        Mockito
                .when(requestRepository.ownerRequest(owner.getId()))
                .thenReturn(listRequest);

        List<Request> listRequestOut = requestRepository.ownerRequest(owner.getId());

        Mockito
                .verify(requestRepository, Mockito.times(1)).ownerRequest(owner.getId());

        Mockito
                .when(itemRequestMapper.toItemRequestDto(request))
                .thenReturn(itemRequestDto);

        Mockito
                .when(itemRepository.itemsForRequestId(owner.getId()))
                .thenReturn(items);

        List<ItemRequestDto> itemRequestDtosOut = listRequestOut.stream()
                .map(itemRequestMapper::toItemRequestDto)
                .peek(x -> {
                    x.setItems(itemRepository.itemsForRequestId(owner.getId()));
                })
                .collect(Collectors.toList());

        Assertions.assertArrayEquals(itemRequestDtosOut.toArray(), list.toArray());
    }

    @Test
    void findItemRequest() {
        Request request = new Request();
        request.setDescription("test");
        request.setId(1);
        User user = new User();
        user.setId(1);
        user.setName("test");
        user.setEmail("test@mail.ru");
        request.setRequestorId(user.getId());
        request.setCreated(Timestamp.from(Instant.now()));
        List<Item> items = List.of();
        ItemRequestDto itemRequestDto = new ItemRequestDto(request.getId(), request.getDescription(), request.getCreated(),
                1L, items);

        Mockito.doNothing()
                .when(userService).searchUser(user.getId());

        Mockito
                .when(requestRepository.existsById(request.getId()))
                .thenReturn(true);

        boolean existRequest = requestRepository.existsById(user.getId());

        Mockito.doNothing()
                .when(itemRequestValidation).itemRequestIdIsEmpty(existRequest);

        Mockito
                .when(requestRepository.findById(request.getId()))
                .thenReturn(Optional.of(request));

        Assertions.assertTrue(requestRepository.findById(request.getId()).isPresent());

        Mockito
                .when(itemRequestMapper.toItemRequestDto(request))
                .thenReturn(itemRequestDto);

        itemRequestMapper.toItemRequestDto(request);

        Mockito.verify(itemRequestMapper, Mockito.times(1)).toItemRequestDto(request);

    }

}