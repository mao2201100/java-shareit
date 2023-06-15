package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comments.Comments;
import ru.practicum.shareit.item.dto.Item;

@Slf4j
@RestController
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {

    private final ItemClient itemClient;

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> findById(@PathVariable("itemId") Long itemId,
                                           @RequestHeader("X-Sharer-User-Id") long ownerId) { // получить вещь по id
        log.info("Executing Get findById: " + itemId);
        return itemClient.findItemById(itemId, ownerId);
    }

    @GetMapping
    public ResponseEntity<Object> itemAllOwnerId(@RequestHeader("X-Sharer-User-Id") long ownerId) {  //получение списка всех вещей конкретного пользователя.
        log.info("Executing Get itemAllOwnerId: " + ownerId);
        return itemClient.itemAllOwnerId(ownerId);
    }

    @GetMapping("search")
    public ResponseEntity<Object> itemSearch(@RequestParam String text,
                                             @RequestHeader("X-Sharer-User-Id") long ownerId) {  //получение списка всех вещей по параметру поиска.
        log.info("Executing Get itemSearch: " + text);
        return itemClient.itemSearch(text, ownerId);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Item item, @RequestHeader("X-Sharer-User-Id") long idUser) { // создать вещь
        log.info("Executing Post createItemRequest");
        return itemClient.create(item, idUser);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> createComments(@PathVariable("itemId") Long itemId, @RequestBody Comments comments,
                                                 @RequestHeader("X-Sharer-User-Id") long idUser) {
        // добавить комментарий
        log.info("Executing Comment createItemRequest");

        return itemClient.createComments(comments, idUser, itemId);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> update(@RequestBody Item item, @PathVariable("itemId") Long itemId, @RequestHeader("X-Sharer-User-Id") long ownerId) { // изменить вещь
        log.info("Executing Patch update");
        return itemClient.update(item, itemId, ownerId);
    }

}
