package ru.practicum.shareit.user.storage;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.List;

@Service
public class UserStorageImpl implements UserStorage {
    @Override
    public User findUserByLogin(String login) {
        return null;
    }

    @Override
    public List<User> readAll() {
        return null;
    }

    @Override
    public User readById(Long userId) {
        return null;
    }

    @Override
    public void create(User user) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public Collection<User> fetchUsersByIds(Collection<Long> ids) {
        return null;
    }
}
