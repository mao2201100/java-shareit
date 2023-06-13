package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserRequestDto;

import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {

    private  final UserClient userClient;

    @GetMapping("/{userId}")
    public ResponseEntity<Object> findById(
            @PathVariable("userId") Long id) { // получить юзера по id
        log.info("Executing Get findById: " + id);
        return userClient.findUserById(id);
    }

    @GetMapping
    public ResponseEntity<Object> readAll() {  //получение списка всех пользователей.
        log.info("Executing Get readAll");
        return userClient.getUsers();
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody UserRequestDto userRequestDto) { // создать пользователя
        log.info("Executing Post createItemRequest user");
        return userClient.create(userRequestDto);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Object> update(
                                         @RequestBody UserRequestDto userRequestDto,
                                         @PathVariable("userId") Long userId) { // изменить пользователя
        log.info("Executing Patch update user id: " + userId);
        return userClient.update(userId, userRequestDto);
    }

    @DeleteMapping("/{userId}") // удаление польльзователя
    public void deleteFriends(
                              @PathVariable Long userId) {
        userClient.deleteUser(userId);
        log.info("Executing Delete  user id: " + userId);
    }
}
