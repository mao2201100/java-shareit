package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.Request;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

/**
 * TODO Sprint add-item-requests.
 */
@Slf4j
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {

    public final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> createItemRequest(@RequestHeader("X-Sharer-User-Id") long userId,
                                                    @RequestBody Request request) { // создать запрос вещи
        log.info("Executing Post createItemRequest");
        return requestClient.create(userId, request);
    }

    @GetMapping
    public ResponseEntity<Object> findAllItemRequestOwner(@RequestHeader("X-Sharer-User-Id") long ownerId) { //получить список своих запросов вместе с данными об ответах на них
        log.info("Executing Get findAllItemRequestOwner");
        return requestClient.findAllItemRequestOwner(ownerId);
    }
    // Для каждого запроса должны указываться описание, дата и время создания и список ответов в формате: id вещи,
    // название, id владельца. Так в дальнейшем, используя указанные id вещей, можно будет получить подробную
    // информацию о каждой вещи. Запросы должны возвращаться в отсортированном порядке от более новых к более
    // старым.

    @GetMapping("/all") // получить список запросов, созданных другими пользователями.
    public ResponseEntity<Object> findAllItemRequestUsers(@PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Long from,
                                                          @Positive @RequestParam(name = "size", defaultValue = "10") Long size,
                                                              @RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Executing Get findAllItemRequestUsers");
        return requestClient.findAllItemRequestUsers(from, size, userId);
    }

    @GetMapping("/{requestId}")
    // получить данные об одном конкретном запросе вместе с данными об ответах на него в том же формате, что и в эндпоинте
    public ResponseEntity<Object> findItemRequest(@PathVariable("requestId") long requestId, @RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Executing Get findItemRequest");
        return requestClient.findItemRequest(requestId, userId);
    }

}
