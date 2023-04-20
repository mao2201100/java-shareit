package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@Slf4j
@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public UserDto findById(@PathVariable("userId") Long id) { // получить юзера по id
        log.info("Executing Get findById: " + id);
        return userService.findUserById(id);
    }

    @GetMapping
    public Collection<UserDto> readAll() {  //получение списка всех пользователей.
        log.info("Executing Get readAll");
        return userService.getUsers();
    }

    @PostMapping
    public UserDto create(@RequestBody User user) { // создать пользователя
        log.info("Executing Post create user");
        return userService.create(user);
    }

    @PatchMapping("/{userId}")
    public UserDto update(@RequestBody User user, @PathVariable("userId") Long userId) { // изменить пользователя
        log.info("Executing Patch update user id: " + userId);
        return userService.update(userId, user);
    }

    @DeleteMapping("/{userId}") // удаление польльзователя
    public void deleteFriends(@PathVariable Long userId) {
        userService.deleteUser(userId);
        log.info("Executing Delete  user id: " + userId);
    }
}
