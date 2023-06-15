package ru.practicum.shareit.comments;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.sql.Timestamp;

@Getter
@Setter
@ConstructorBinding
public class Comments {
    private long id;
    private String text;
    private long itemId;
    private long authorId;
    private Timestamp created;
}
