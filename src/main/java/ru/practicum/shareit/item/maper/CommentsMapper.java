package ru.practicum.shareit.item.maper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.comments.Comments;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.user.UserRepository;

@Component
public class CommentsMapper {
    private UserRepository userRepository;

    public CommentDto toCommentsDto(Comments comments) {
        CommentDto commentDto = new CommentDto();
        BeanUtils.copyProperties(comments, commentDto);
        commentDto.setAuthorName(comments.getAuthorId() != 0 ?
                userRepository.findById(commentDto.getAuthor_id()).orElse(null).getName() : null);
        return commentDto;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
