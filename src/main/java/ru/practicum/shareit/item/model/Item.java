package ru.practicum.shareit.item.model;

import lombok.Data;
import ru.practicum.shareit.comments.Comments;

import javax.persistence.*;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Entity
@Table(name = "item")
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // уникальный идентификатор вещ
    @Column(name = "name")
    private String name; //  краткое название
    @Column(name = "description")
    private String description; //  развёрнутое описание
    @Column(name = "available")
    private Boolean available; // статус о том, доступна или нет вещь для аренды
    @Column(name = "owner")
    private String owner; // владелец вещи
    @Column(name = "request")
    private String request; // если вещь была создана по запросу другого пользователя, то в этом
    //поле будет храниться ссылка на соответствующий запрос
    @Column(name = "owner_id")
    private long ownerId;
    @Column(name = "request_id")
    private long requestId;

    @OneToMany
    @JoinColumn(name = "item_id")
    private List<Comments> comments;
}
