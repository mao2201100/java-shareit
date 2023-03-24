package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.List;

public interface UserStorage {
    User findUserByLogin(String login);

    List<User> readAll();

    User readById(Long userId); // найти юзера по id

    void create(User user);

    void update(User user); // изменить пользователя

    Collection<User> fetchUsersByIds(Collection<Long> ids);
}
