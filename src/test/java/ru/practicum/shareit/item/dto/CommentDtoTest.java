package ru.practicum.shareit.item.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommentDtoTest {

    private static CommentDto commentDto = new CommentDto();

    @BeforeAll
    static void setup() {
        commentDto.setId(1L);
        commentDto.setText("testComments");
        commentDto.setCreated(Timestamp.valueOf(LocalDateTime.parse("2023-06-05T01:35:19.8065531")));
        commentDto.setAuthorId(1L);
        commentDto.setAuthorName("testAuthor");
        commentDto.setItemId(1L);
    }

    @Test
    void getId() {
        assertEquals(1L, commentDto.getId());
    }

    @Test
    void getText() {
        assertEquals("testComments", commentDto.getText());

    }

    @Test
    void getItemId() {
        assertEquals(1L, commentDto.getItemId());
    }

    @Test
    void getAuthorId() {
        assertEquals(1L, commentDto.getAuthorId());
    }

    @Test
    void getAuthorName() {
        assertEquals("testAuthor", commentDto.getAuthorName());
    }

    @Test
    void getCreated() {
        assertEquals("2023-06-05 01:35:19.8065531", commentDto.getCreated().toString());
    }

}