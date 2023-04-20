package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

@Data
@RequiredArgsConstructor
public class CommentDto {
    private long id;
    private String text;
    private long item_id;
    private long author_id;
    private String authorName;
    private Timestamp created;
}
