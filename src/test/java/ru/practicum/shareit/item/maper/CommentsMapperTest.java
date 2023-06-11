package ru.practicum.shareit.item.maper;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.practicum.shareit.comments.Comments;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.sql.Timestamp;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
class CommentsMapperTest {
    @MockBean
    UserRepository userRepository;
    @Autowired
    CommentsMapper commentsMapper;

    private static User user = new User();

    @BeforeAll
    static void setup() {
        user.setName("test");
        user.setEmail("mail@mail.ru");
    }

    @Test
    void toCommentsDtoAuthorBead() {
        Comments comments = new Comments();
        comments.setId(1);
        comments.setCreated(Timestamp.valueOf("2023-05-22 23:00:29"));
        comments.setText("test");
        comments.setItemId(1);

        CommentDto commentDto = commentsMapper.toCommentsDto(comments);
        Assert.assertEquals(1, commentDto.getId());
        Assert.assertEquals("test", commentDto.getText());
        Assert.assertEquals(0, commentDto.getAuthorId());
        Assert.assertEquals("2023-05-22 23:00:29.0", commentDto.getCreated().toString());
        Assert.assertEquals(1, commentDto.getItemId());
        Assert.assertEquals(null, commentDto.getAuthorName());
    }

    @Test
    void toCommentsDto() {
        Comments comments = new Comments();
        comments.setId(1);
        comments.setCreated(Timestamp.valueOf("2023-05-22 23:00:29"));
        comments.setAuthorId(1);
        comments.setText("test");
        comments.setItemId(1);
        User user = new User();
        user.setId(1);
        user.setEmail("test@mail.ru");
        user.setName("test");
        Mockito
                .when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(user));
        commentsMapper.toCommentsDto(comments);
        Assert.assertEquals(1, comments.getId());
        Assert.assertEquals("test", comments.getText());
        Assert.assertEquals(1, comments.getAuthorId());
        Assert.assertEquals("2023-05-22 23:00:29.0", comments.getCreated().toString());
        Assert.assertEquals(1, comments.getItemId());

    }

}