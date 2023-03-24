package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.util.Collection;

public interface UserService {

    UserDto findUserById(Long id);

    UserDto create(User user); // добавить пользователя

    Collection<UserDto> getUsers(); // показать всех пользователей

    UserDto update(Long userId, User user); // изменить пользователя

    void deleteUser(long userId); // удаление из друзей
}
