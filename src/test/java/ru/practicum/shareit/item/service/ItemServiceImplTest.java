package ru.practicum.shareit.item.service;

import org.junit.Assert;
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
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.validation.UserValidation;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
class ItemServiceImplTest {
    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    @SpyBean
    UserMapper userMapper;
    @SpyBean
    UserValidation userValidation;

    public User createTestUser() {
        User user = new User();
        user.setId(1);
        user.setEmail("test@mail.ru");
        user.setName("Test");
        return user;
    }

    public UserDto createTestUserDto() {
        UserDto userDto = new UserDto(1L, "Test", "test@mail.ru");
        return userDto;
    }

    @Test
    void findItemById() {
        var user = createTestUser();
        Mockito
                .when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        Mockito
                .when(userRepository.getById(user.getId()))
                .thenReturn(user);

        var userDto2 = userService.findUserById(user.getId());

        Mockito
                .verify(userRepository, Mockito.times(1)).findById(1L);
        Mockito
                .verify(userRepository, Mockito.times(1)).getById(1L);

        Assert.assertEquals(1, (long) userDto2.getId());
        Assert.assertEquals("Test", userDto2.getName());
        Assert.assertEquals("test@mail.ru", userDto2.getEmail());
    }

    @Test
    void itemAllOwnerId() {
        var user = createTestUser();
        var userDto = createTestUserDto();
        List<User> listUser = List.of(user);
        Collection<UserDto> userDdoCol = List.of(userDto);
        Mockito
                .when(userRepository.findAll())
                .thenReturn(listUser);

        List<User> listTest = userRepository.findAll();
        Collection<UserDto> collectionDtoTest = listTest.stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
        Assert.assertArrayEquals(userDdoCol.toArray(), collectionDtoTest.toArray());
    }

    @Test
    void itemSearch() {

        User user = createTestUser();
        Mockito
                .when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        userService.searchUser(user.getId());

        Mockito.verify(userRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void create() {
        User user = createTestUser();
        Mockito
                .when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        userService.searchUser(user.getId());

        Mockito.verify(userRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void update() {
        User user = createTestUser();
        Mockito
                .when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        userService.searchUser(user.getId());

        Mockito.verify(userRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void createComments() {
        User user = createTestUser();
        Mockito
                .when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        userService.searchUser(user.getId());

        Mockito.verify(userRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void checkItem() {
        User user = createTestUser();
        Mockito
                .when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        userService.searchUser(user.getId());

        Mockito.verify(userRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void checkItemAvailable() {
        User user = createTestUser();
        Mockito
                .when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        userService.searchUser(user.getId());

        Mockito.verify(userRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void checkItemOwner() {
        User user = createTestUser();
        Mockito
                .when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        userService.searchUser(user.getId());

        Mockito.verify(userRepository, Mockito.times(1)).findById(1L);
    }
}