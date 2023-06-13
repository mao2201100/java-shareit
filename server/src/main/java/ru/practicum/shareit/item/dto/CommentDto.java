package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

@Data
@RequiredArgsConstructor
public class CommentDto {
    private long id;
    private String text;
    private long itemId;
    private long authorId;
    private String authorName;
    private Timestamp created;
}
