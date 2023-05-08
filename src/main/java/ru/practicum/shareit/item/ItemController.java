package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comments.Comments;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@Slf4j
@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{itemId}")
    public ItemDto findById(@PathVariable("itemId") Long itemId,
                            @RequestHeader("X-Sharer-User-Id") long ownerId) { // получить вещь по id
        log.info("Executing Get findById: " + itemId);
        return itemService.findItemById(itemId, ownerId);
    }

    @GetMapping
    public Collection<ItemDto> itemAllOwnerId(@RequestHeader("X-Sharer-User-Id") long ownerId) {  //получение списка всех вещей конкретоного пользователя.
        log.info("Executing Get itemAllOwnerId: " + ownerId);
        return itemService.itemAllOwnerId(ownerId);
    }

    @GetMapping("search")
    public Collection<ItemDto> itemSearch(@RequestParam String text,
                                          @RequestHeader("X-Sharer-User-Id") long ownerId) {  //получение списка всех вещей по параметру поиска.
        log.info("Executing Get itemSearch: " + text);
        return itemService.itemSearch(text, ownerId);
    }

    @PostMapping
    public ItemDto create(@RequestBody Item item, @RequestHeader("X-Sharer-User-Id") long idUser) { // создать вещь
        log.info("Executing Post createItemRequest");
        return itemService.create(item, idUser);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto createComments(@PathVariable("itemId") Long itemId, @RequestBody Comments comments,
                                     @RequestHeader("X-Sharer-User-Id") long idUser) {
        // добавить комментарий
        log.info("Executing Comment createItemRequest");

        return itemService.createComments(comments, idUser, itemId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestBody Item item, @PathVariable("itemId") Long itemId, @RequestHeader("X-Sharer-User-Id") long ownerId) { // изменить вещь
        log.info("Executing Patch update");
        return itemService.update(item, itemId, ownerId);
    }

}
