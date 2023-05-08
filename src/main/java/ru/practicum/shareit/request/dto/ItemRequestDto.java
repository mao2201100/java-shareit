package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.item.model.Item;

import java.sql.Timestamp;
import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@Getter
@Setter
@AllArgsConstructor
public class ItemRequestDto {
    private long id; // уникальный идентификатор запроса
    private String description; // описание запроса
    private Timestamp created; // дата и время создания запроса;
    private Long requestorId; // id пользователя делающего запрос
    private List<Item> items; // вещи добавленные для этого запроса


}
