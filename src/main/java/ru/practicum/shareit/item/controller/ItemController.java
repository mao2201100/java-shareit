package ru.practicum.shareit.item.controller;

import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    @GetMapping("/{itemId}")
    public ItemDto findById(@PathVariable("itemId") Long itemId) { // получить вещь по id
        return service.findItemById(itemId);
    }

    @GetMapping
    public Collection<ItemDto> itemAllOwnerId(@RequestHeader("X-Sharer-User-Id") long ownerId) {  //получение списка всех вещей конкретоного пользователя.
        return service.itemAllOwnerId(ownerId);
    }

    @GetMapping("search")
    public Collection<ItemDto> itemSearch(@RequestParam String text) {  //получение списка всех вещей по параметру поиска.
        return service.itemSearch(text);
    }

    @PostMapping
    public ItemDto create(@RequestBody Item item, @RequestHeader("X-Sharer-User-Id") long idUser) { // создать вещь
        return service.create(item, idUser);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestBody Item item, @PathVariable("itemId") Long itemId, @RequestHeader("X-Sharer-User-Id") long ownerId) { // изменить вещь
        return service.update(item, itemId, ownerId);
    }

}
