package ru.practicum.shareit.user.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserService userService;
    @SpyBean
    private UserRepository userRepository;
    @SpyBean
    UserMapper userMapper;

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
    void findUserById() {
        User user = createTestUser();
        Mockito
                .when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        Mockito
                .when(userRepository.getById(user.getId()))
                .thenReturn(user);

        UserDto userDto2 = userService.findUserById(user.getId());

        Mockito
                .verify(userRepository, Mockito.times(1)).findById(1L);
        Mockito
                .verify(userRepository, Mockito.times(1)).getById(1L);

        Assert.assertEquals(1, (long) userDto2.getId());
        Assert.assertEquals("Test", userDto2.getName());
        Assert.assertEquals("test@mail.ru", userDto2.getEmail());
    }

    @Test
    void getUsers() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void searchUser() {
    }

    @Test
    void deleteUser() {
    }
}