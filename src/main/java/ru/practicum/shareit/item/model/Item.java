package ru.practicum.shareit.item.model;

import lombok.Data;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class Item {
    private Long id;  // уникальный идентификатор вещ
    private String name; //  краткое название
    private  String description; //  развёрнутое описание
    private Boolean available; // статус о том, доступна или нет вещь для аренды
    private String owner; // владелец вещи
    private String request; // если вещь была создана по запросу другого пользователя, то в этом
    //поле будет храниться ссылка на соответствующий запрос
}
