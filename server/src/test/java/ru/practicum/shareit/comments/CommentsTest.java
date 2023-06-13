package ru.practicum.shareit.comments;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommentsTest {
    private static Comments comments = new Comments();

    @BeforeAll
    static void setup() {
        comments.setId(1L);
        comments.setText("testComments");
        comments.setCreated(Timestamp.valueOf(LocalDateTime.parse("2023-06-05T01:35:19.8065531")));
        comments.setAuthorId(1L);
        comments.setItemId(1L);
    }

    @Test
    void getId() {
        assertEquals(1L, comments.getId());
    }

    @Test
    void getText() {
        assertEquals("testComments", comments.getText());

    }

    @Test
    void getItemId() {
        assertEquals(1L, comments.getItemId());
    }

    @Test
    void getAuthorId() {
        assertEquals(1L, comments.getAuthorId());
    }


    @Test
    void getCreated() {
        assertEquals("2023-06-05 01:35:19.8065531", comments.getCreated().toString());
    }

}

