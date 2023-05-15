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
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.Request;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.maper.ItemRequestMapper;
import ru.practicum.shareit.request.validation.ItemRequestValidation;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.service.UserService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

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
    //    @MockBean
//    private UserMapper userMapper;
//    @MockBean
//    private UserValidation userValidation;
//    @MockBean
//    private EntityManager entityManager;
//
//    @MockBean
//    private RequestRepository requestRepository;
    @MockBean
    private ItemRequestMapper itemRequestMapper;
//    @MockBean
//    private BookingServiceImpl bookingService;

    @Test
    void create() {
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
        ItemRequestDto itemRequestDto = new ItemRequestDto(request.getId(), " ", request.getCreated(),
                1L, items);
        // ItemRequestValidation itemRequestValidation = Mockito.mock(ItemRequestValidation.class);
        //UserService userService = Mockito.mock(UserServiceImpl.class);
        //RequestRepository requestRepository = Mockito.mock(RequestRepository.class);
        // ItemRequestMapper itemRequestMapper = Mockito.mock(ItemRequestMapper.class);
        //ItemRequestDto itemRequestDto = itemRequestMapper.toItemRequestDto(request);
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
//        Mockito
//                .verify(itemRequestValidation, times(1))
//                .itemRequest(request);
//
//        Mockito.verify(userService, Mockito.times(1))
//                .searchUser(anyLong());
//
//        Mockito
//                .when(requestRepository.save(new Request()))
//                .thenReturn(request);
//        Mockito
//                .when(itemRequestMapper.toItemRequestDto(new Request()))
//                .thenReturn(itemRequestDto);
//
//        Mockito.verify(requestRepository, Mockito.times(1))
//                .save(request);
//
//        Mockito
//                .when(itemRequestMapper.toItemRequestDto(new Request()))
//                .thenReturn(itemRequestDto);
//        assertEquals(1, itemRequestDto.getId());
    }

}