package ru.practicum.shareit.item.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.maper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.validation.ItemValidation;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {
    private Map<Long, Item> items = new HashMap<>();
    private final ItemValidation validation;
    private final UserServiceImpl userService;
    private long sequenceId = 1;

    public ItemServiceImpl(ItemValidation validation, UserServiceImpl userService) {
        this.validation = validation;
        this.userService = userService;
    }

    @Override
    public ItemDto findItemById(Long itemId) {
        if(items.get(itemId) == null) {
            validation.searchItem();
        }
        return ItemMapper.toItemDto(items.get(itemId));

    }

    @Override
    public Collection<ItemDto> itemAllOwnerId(Long ownerId) {
        String ownerName = userService.findUserById(ownerId).getName();
        Map<Long, Item> itemAllOwnerId = new HashMap<>();
        for (Map.Entry<Long, Item> i : items.entrySet()) {
            if (i.getValue().getOwner().equals(ownerName)) {
                itemAllOwnerId.put(i.getKey(), i.getValue());
            }
        }
        log.info("Пользователь id: " + ownerId + " имеет: " + itemAllOwnerId.size() + " вещей");
        return itemAllOwnerId.values().stream().map(ItemMapper::toItemDto).collect(Collectors.toList());
    }

    @Override
    public Collection<ItemDto> itemSearch(String text) {
        Map<Long, Item> foundItem = new HashMap<>();
        for (Map.Entry<Long, Item> i : items.entrySet()) {
            if ((i.getValue().getName().toLowerCase().equals(text.toLowerCase()) ||
                    i.getValue().getDescription().toLowerCase().equals(text.toLowerCase()) ||
                    i.getValue().getName().toLowerCase().lastIndexOf(text.toLowerCase()) != -1 ||
                    i.getValue().getDescription().toLowerCase().lastIndexOf(text.toLowerCase()) != -1)
                    && i.getValue().getAvailable()
                    && !text.isEmpty()) {
                foundItem.put(i.getKey(), i.getValue());
            }
        }
        log.info("По запросу " + text + " Найдено: " + foundItem.size() + " вещей");
        return foundItem.values().stream().map(ItemMapper::toItemDto).collect(Collectors.toList());
    }

    @Override
    public ItemDto create(Item item, long idUser) {
        validation.item(item);
        item.setId(sequenceId);
        item.setOwner(userService.findUserById(idUser).getName());
        items.put(item.getId(), item);
        sequenceId += 1;
        log.info("Пользователь id: " + idUser + " добавил вещь: " + item.getName());
        return ItemMapper.toItemDto(item);
    }

    @Override
    public ItemDto update(Item item, Long itemId, Long ownerId) {   // изменить ващь
        searchUser(itemId);
        String owner = userService.findUserById(ownerId).getName();
        String ownerItem = items.get(itemId).getOwner();
        validation.ownerItem(owner, ownerItem);
        if (item.getName() != null) {
            items.get(itemId).setName(item.getName());
            log.info("Изменена вещь id:" + itemId + " название: " + item.getName());
        }
        if (item.getDescription() != null) {
            items.get(itemId).setDescription(item.getDescription());
            log.info("Изменена вещь id:" + itemId + " описание: " + item.getDescription());
        }
        if (item.getAvailable() != null) {
            items.get(itemId).setAvailable(item.getAvailable());
            log.info("Изменена вещь id:" + itemId + " статус: " + item.getAvailable());
        }

        return ItemMapper.toItemDto(items.get(itemId));
    }

    public void searchUser(Long itemId) {  // поиск вещи
        if (items.get(itemId) == null) {
            validation.searchItem();
        }
    }
}
