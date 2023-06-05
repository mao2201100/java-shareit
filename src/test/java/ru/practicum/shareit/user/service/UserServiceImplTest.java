package ru.practicum.shareit.user.service;

import org.junit.Assert;
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
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.validation.UserValidation;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest {
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserValidation userValidation;
    @MockBean
    private EntityManager entityManager;

    private static final User userTest = new User();

    @BeforeAll
    static void setup() {
        userTest.setId(1L);
        userTest.setEmail("test@mail.ru");
        userTest.setName("Test");
    }


    public User createTestUser() {
        User user = new User();
        user.setId(1L);
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
    void getUsers() {
        User user = createTestUser();
        user.setId(1L);
        user.setName("Test");
        user.setEmail("test@mail.ru");
        List<User> userList = List.of(user);

        Mockito
                .when(userRepository.findAll())
                .thenReturn((userList));

        Collection<UserDto> userCollection = userService.getUsers();
        List<UserDto> userDtoList = (List) userCollection;

        assertEquals(1l, userDtoList.get(0).getId());
        assertEquals("Test", userDtoList.get(0).getName());
        assertEquals("test@mail.ru", userDtoList.get(0).getEmail());
    }

    @Test
    void create() {

        Mockito.doNothing()
                .when(entityManager).persist(Mockito.any(User.class));

        userService.create(createTestUser());
        UserDto userDto = UserMapper.toUserDto(userTest);
        assertEquals(1L, userDto.getId());
        assertEquals("test@mail.ru", userDto.getEmail());
        assertEquals("Test", userDto.getName());
    }

    @Test
    void update() {
        User userUpdate = new User();
        userUpdate.setName("TestUpdate");
        userUpdate.setEmail("TestUpdate@mail.ru");

        User userTestUpdate = new User();
        userTestUpdate.setId(userTest.getId());
        userTestUpdate.setName("TestUpdate");
        userTestUpdate.setEmail("TestUpdate@mail.ru");

        Mockito
                .when(userRepository.getById(Mockito.anyLong()))
                .thenReturn(userTest);

        Mockito
                .when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(userTest));

        Mockito
                .when(userRepository.save(Mockito.any(User.class)))
                .thenReturn(userTestUpdate);


        UserDto userDto = userService.update(userTest.getId(), userUpdate);
        assertEquals(1L, userDto.getId());
        assertEquals("TestUpdate", userDto.getName());
        assertEquals("TestUpdate@mail.ru", userDto.getEmail());
    }

    @Test
    void updateEmptyUser() {
        assertThrows(NotFoundException.class, () -> userService.update(5L, userTest));
        try {
            userService.update(5L, userTest);
        } catch (Exception e) {
            Assert.assertEquals("Пользователь не найден",
                    e.getMessage());
        }
    }

    @Test
    void updateValidationCreateName() {
        User userUpdate = new User();
        userUpdate.setId(4L);
        userUpdate.setName("");
        userUpdate.setEmail("TestUpdate@mail.ru");

        Mockito
                .when(userRepository.getById(Mockito.anyLong()))
                .thenReturn(userUpdate);

        Mockito
                .when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(userUpdate));

        assertThrows(ValidationException.class, () -> userService.update(userUpdate.getId(), userTest));
        try {
            userService.update(userUpdate.getId(), userTest);
        } catch (Exception e) {
            Assert.assertEquals("логин не может быть пустым и содержать пробелы ",
                    e.getMessage());
        }
    }

    @Test
    void updateValidationCreateEmail() {
        User userUpdate = new User();
        userUpdate.setId(4L);
        userUpdate.setName("TestName");
        userUpdate.setEmail("TestUpdatemail.ru");

        Mockito
                .when(userRepository.getById(Mockito.anyLong()))
                .thenReturn(userUpdate);

        Mockito
                .when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(userUpdate));

        assertThrows(ValidationException.class, () -> userService.update(userUpdate.getId(), userTest));
        try {
            userService.update(userUpdate.getId(), userTest);
        } catch (Exception e) {
            Assert.assertEquals("электронная почта не может быть пустой и должна содержать символ @",
                    e.getMessage());
        }
    }

    @Test
    void searchUser() {

        User user = createTestUser();
        Mockito
                .when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        userService.searchUser(user.getId());

        Mockito.verify(userRepository, Mockito.times(1)).findById(1L);

    }

    @Test
    void searchUserNotFind() { // не знаю как протестировать неготивный сценарий.
        User user = null;
        Mockito
                .when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(user));

        Assert.assertThrows(NotFoundException.class, () -> userService.searchUser(33L));

        try {
            userValidation.userNotFound();
        } catch (Exception e) {
            Assert.assertEquals("Пользователь не найден",
                    e.getMessage());
            return;
        }
    }


    @Test
    void deleteUser() {
        User user = createTestUser();
        Mockito
                .when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));
        Mockito
                .when(userRepository.getReferenceById(user.getId()))
                .thenReturn(user);
        userService.deleteUser(user.getId());
        Mockito.verify(userRepository, Mockito.times(1))
                .delete(user);
    }

    @Test
    void deleteEmptyUser() {
        assertThrows(NotFoundException.class, () -> userService.deleteUser(1L));
        try {
            userService.deleteUser(1L);
        } catch (Exception e) {
            Assert.assertEquals("Пользователь не найден",
                    e.getMessage());
        }
    }

}