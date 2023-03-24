package ru.practicum.shareit.user.controller;

import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/{userId}")
    public UserDto findById(@PathVariable("userId") Long id) { // получить юзера по id
        return service.findUserById(id);
    }

    @GetMapping
    public Collection<UserDto> readAll() {  //получение списка всех пользователей.
        return service.getUsers();
    }

    @PostMapping
    public UserDto create(@RequestBody User user) { // создать пользователя
        return service.create(user);
    }

    @PatchMapping("/{userId}")
    public UserDto update(@RequestBody User user, @PathVariable("userId") Long userId) { // изменить пользователя
        return service.update(userId, user);
    }

    @DeleteMapping("/{userId}") // удаление польльзователя
    public void deleteFriends(@PathVariable Long userId) {
        service.deleteUser(userId);
    }
}
