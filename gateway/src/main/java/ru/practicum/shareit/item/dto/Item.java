package ru.practicum.shareit.item.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConstructorBinding;
import ru.practicum.shareit.comments.Comments;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Getter
@Setter
@ConstructorBinding
public class Item {
    private Long id;  // уникальный идентификатор вещ
    private String name; //  краткое название
    private String description; //  развёрнутое описание
    private Boolean available; // статус о том, доступна или нет вещь для аренды
    private String owner; // владелец вещи
    //поле будет храниться ссылка на соответствующий запрос
    private long ownerId;
    private long requestId;

    private List<Comments> comments;
}
