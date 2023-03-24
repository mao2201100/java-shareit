package ru.practicum.shareit.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.validation.UserValidation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final Map<Long, User> users = new HashMap<>();
    private long sequenceId = 1;
    private UserValidation validation = new UserValidation();

    @Override
    public UserDto findUserById(Long id) { // получить юзера по id
        if (!users.containsKey(id)) {
            validation.searchUser();
        }
        return UserMapper.toUserDto(users.get(id));
    }

    @Override
    public Collection<UserDto> getUsers() { // получить список всех пользователей
        return users.values().stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto create(User user) {  // добавление юзера
        validation.create(user);
        user.setId(sequenceId);
        duplicateEmail(user);
        user.setName(user.getName());
        user.setEmail(user.getEmail());
        users.put(user.getId(), user);
        sequenceId += 1;
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto update(Long userId, User user) {  // обновление пользователя
        searchUser(userId);
        validation.create(users.get(userId));
        if (user.getName() != null) {
            users.get(userId).setName(user.getName());
            log.info("Изменен пользователь id:" + userId + " логин: " + user.getName());
        }
        if (user.getEmail() != null) {
            duplicateEmail(user);
            users.get(userId).setEmail(user.getEmail());
            log.info("Изменен пользователь id:" + userId + " email: " + user.getEmail());
        }
        return UserMapper.toUserDto(users.get(userId));
    }

    public void searchUser(Long userId) {  // поиск пользователя
        if (users.get(userId) == null) {
            validation.searchUser();
        }
    }

    @Override
    public void deleteUser(long userId) { // удаление пользователя
        if (!users.containsKey(userId)) {
            validation.searchUser();
        }
        users.remove(userId);
    }

    public void duplicateEmail(User user) {  // проверка на существующий email при добавлении пользователя
        for (Map.Entry<Long, User> i : users.entrySet()) {
            if (i.getValue().getEmail().equals(user.getEmail()) && i.getValue().getId() != user.getId()) {
                validation.duplicateEmail();
                return;
            }
        }
    }

}
