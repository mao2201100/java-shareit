package ru.practicum.shareit.request.service;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.Request;
import ru.practicum.shareit.request.RequestRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.maper.ItemRequestMapper;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
class ItemRequestServiceImplTest {
    @Autowired
    private ItemRequestService itemRequestService;
    @Autowired
    private ItemRequestMapper itemRequestMapper;
    @MockBean
    private RequestRepository requestRepository;
    @MockBean
    private UserRepository userRepository;

    private static User user = new User();

    @BeforeAll
    static void setup() {
        user.setId(1L);
        user.setName("test");
        user.setEmail("test@mail.ru");
    }

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
        Mockito
                .when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(user));

        Mockito
                .when(requestRepository.save(request))
                .thenReturn(request);

        ItemRequestDto isItemRequestDtoCreated = itemRequestService.create(request, user.getId());
        boolean create = isItemRequestDtoCreated != null ? true : false;
        Assertions.assertTrue(create);

    }

    @Test
    void findAllItemRequestUsers() {
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
                .when(requestRepository.findAll())
                .thenReturn(list);

        Collection<ItemRequestDto> itemRequestDtoCollection = itemRequestService.findAllItemRequestUsers(null, null, user.getId());
        List<ItemRequestDto> itemRequestDtoList = (List) itemRequestDtoCollection;
        assertEquals("test", itemRequestDtoList.get(0).getDescription());
        assertEquals(1, itemRequestDtoList.get(0).getId());

        try {
            itemRequestService.findAllItemRequestUsers(0L, 0L, user.getId());
        } catch (Exception e) {
            Assert.assertEquals("не верно указано количество элементов при выводе",
                    e.getMessage());
        }

        Collection<ItemRequestDto> iReDtoCollectionFromSize = itemRequestService.findAllItemRequestUsers(0L, 1L, user.getId());
        List<ItemRequestDto> iReDtoListFromSize = (List) iReDtoCollectionFromSize;
        assertEquals(0, iReDtoListFromSize.size());
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
        List<Request> listRequest = List.of(request);

        Mockito
                .when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(owner));
        Mockito
                .when(requestRepository.ownerRequest(Mockito.anyLong()))
                .thenReturn(listRequest);

        List<ItemRequestDto> itemRequestDtoList = itemRequestService.findAllItemRequestOwner(user.getId());

        assertEquals(1L, itemRequestDtoList.get(0).getId());
        assertEquals("test", itemRequestDtoList.get(0).getDescription());
        assertEquals(1L, itemRequestDtoList.get(0).getRequestorId());
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

        Mockito
                .when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(user));

        Mockito
                .when(requestRepository.existsById(request.getId()))
                .thenReturn(true);

        Mockito
                .when(requestRepository.findById(request.getId()))
                .thenReturn(Optional.of(request));


        itemRequestMapper.toItemRequestDto(request);

        ItemRequestDto itemRequestDtoTest = itemRequestService.findItemRequest(request.getId(), user.getId());

        assertEquals(1L, itemRequestDtoTest.getId());
        assertEquals("test", itemRequestDtoTest.getDescription());
        assertEquals(1L, itemRequestDtoTest.getRequestorId());
    }

}