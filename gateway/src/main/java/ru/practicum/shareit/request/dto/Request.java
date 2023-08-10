package ru.practicum.shareit.request.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConstructorBinding;
import ru.practicum.shareit.item.dto.Item;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ConstructorBinding
public class Request {

    private long id; // уникальный идентификатор запроса
    private String description; // описание запроса
    private Timestamp created; // дата и время создания запроса;
    private Long requestorId; // id пользователя делающего запрос
    private List<Item> items = new ArrayList<>();
}

