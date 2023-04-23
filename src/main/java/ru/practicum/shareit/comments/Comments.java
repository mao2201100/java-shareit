package ru.practicum.shareit.comments;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConstructorBinding;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "comments")
@Getter
@Setter
@ConstructorBinding
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "text")
    private String text;
    @Column(name = "item_id")
    private long itemId;
    @Column(name = "author_id")
    private long authorId;
    @Column(name = "created")
    private Timestamp created;
}
