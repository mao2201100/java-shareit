package ru.practicum.shareit.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConstructorBinding;
import ru.practicum.shareit.item.model.Item;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "requests")
@Getter
@Setter
@ConstructorBinding
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // уникальный идентификатор запроса
    @Column(name = "description")
    private String description; // описание запроса
    @Column(name = "created")
    private Timestamp created; // дата и время создания запроса;
    @Column(name = "requestor_id")
    private Long requestorId; // id пользователя делающего запрос

    @OneToMany(mappedBy = "requestId")
    private List<Item> items = new ArrayList<>();
}

