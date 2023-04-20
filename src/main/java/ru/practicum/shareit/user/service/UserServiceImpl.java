package ru.practicum.shareit.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.validation.UserValidation;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private EntityManager entityManager;
    private UserValidation validation = new UserValidation();

    public UserServiceImpl(EntityManager entityManager, UserRepository userRepository) {
        this.entityManager = entityManager;
        this.userRepository = userRepository;
    }

    @Override
    public UserDto findUserById(Long id) { // получить юзера по id    ++++
        searchUser(id);
        return UserMapper.toUserDto(userRepository.getById(id));
    }

    @Override
    public Collection<UserDto> getUsers() { // получить список всех пользователей  +++
        return userRepository.findAll().stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDto create(User user) {  // добавление юзера +++
        validation.create(user);
        entityManager.persist(user); // добавление в базу юзара
        return UserMapper.toUserDto(user);
    }

    @Override
    @Transactional
    public UserDto update(Long userId, User user) {  // обновление пользователя  ++++
        searchUser(userId);
        User user1 = userRepository.getById(userId);
        validation.create(userRepository.getById(userId));
        if (user.getName() != null) {
            user1.setName(user.getName());
            userRepository.save(user1);
            log.info("Изменен пользователь id:" + userId + " логин: " + user.getName());
        }
        if (user.getEmail() != null) {
            user1.setEmail(user.getEmail());
            userRepository.save(user1);
            log.info("Изменен пользователь id:" + userId + " email: " + user.getEmail());
        }

        return UserMapper.toUserDto(userRepository.getById(userId));
    }

    public void searchUser(Long userId) {  // поиск пользователя  +++
        if (userRepository.findById(userId).isEmpty()) {
            validation.userNotFound();
        }
    }

    @Override
    public void deleteUser(long userId) { // удаление пользователя ++++
        searchUser(userId);
        userRepository.delete(userRepository.getReferenceById(userId));
    }

}
