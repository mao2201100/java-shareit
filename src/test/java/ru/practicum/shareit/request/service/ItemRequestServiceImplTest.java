package ru.practicum.shareit.request.service;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.Request;
import ru.practicum.shareit.request.RequestRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.maper.ItemRequestMapper;
import ru.practicum.shareit.request.validation.ItemRequestValidation;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.service.UserServiceImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.nio.charset.CoderMalfunctionError;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
class ItemRequestServiceImplTest {
    @Autowired
    ItemRequestService itemRequestService;
    @MockBean
    private ItemRequestValidation mockItemRequestValidation;
    @MockBean
    private UserServiceImpl mockUserServiceImpl;
    @MockBean
    RequestRepository mockRequestRepository;
    @MockBean
    ItemRequestMapper mockItemRequestMapper;
    User user = new User();
    Request request = new Request();

    @Test
    void create() {
        request.setId(1);
        request.setDescription("описание");
        user.setName("testUser");
        user.setEmail("test@mail.ru");
        mockUserServiceImpl.create(user);
        ItemRequestDto itemRequestDto = itemRequestService.create(request, user.getId());
        verify(mockItemRequestValidation, times(1)).itemRequest(request);
        verify(mockUserServiceImpl, times(1)).searchUser(user.getId());
        verify(mockRequestRepository, times(1)).save(request);
        assertEquals(request.getId(), itemRequestDto.getId());
        assertEquals(request.getDescription(), itemRequestDto.getDescription());
        assertEquals(user.getId(), itemRequestDto.getRequestorId());
    }

    @Test
    void findAllItemRequestUsersAll() {
        user.setId(1);
        itemRequestService.findAllItemRequestUsers(null, null, user.getId());
        verify(mockRequestRepository, times(1)).findAll();
    }

    @Test
    void findAllItemRequestOwner() {
        user.setId(1);
        itemRequestService.findAllItemRequestOwner(user.getId());
        verify(mockRequestRepository, times(1)).ownerRequest(user.getId());
    }

    @Test
    void findItemRequest() {
        itemRequestService.findItemRequest(request.getId(), user.getId());
        verify(mockRequestRepository, times(1)).existsById(request.getId());

    }
}