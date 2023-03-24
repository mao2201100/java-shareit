package ru.practicum.shareit.item.model.dto;

import lombok.Data;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class ItemDto {
    private Long id;  // уникальный идентификатор вещ
    private String name; //  краткое название
    private String description; //  развёрнутое описание
    private Boolean available; // статус о том, доступна или нет вещь для аренды
    private String owner; // владелец вещи
    private String request; // если вещь была создана по запросу другого пользователя, то в этом
    //поле будет храниться ссылка на соответствующий запрос

    public ItemDto(Long id, String name, String description, Boolean available, String request) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.request = request;
    }
}
