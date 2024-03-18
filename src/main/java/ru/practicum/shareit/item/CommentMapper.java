package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentFromClientDto;
import ru.practicum.shareit.item.dto.CommentInfoDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommentMapper {

    public static Comment toComment(ItemRepository itemRepository,
                             UserRepository userRepository,
                             Long authorId,
                             Long itemId,
                             CommentFromClientDto commentDto,
                             LocalDateTime time) {

        Comment comment = new Comment();
        comment.setText(commentDto.getText());
        Item item = itemRepository.findById(itemId).orElseThrow(() ->
                new NotFoundException("Запрашиваемый обьект отсутствует в базе"));
        comment.setItem(item);
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        comment.setAuthor(author);
        comment.setCreated(time);

        return comment;

    }

    public static CommentDto toCommentDto(ItemMapper itemMapper,
                                   UserMapper userMapper,
                                   Comment comment) {

        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        commentDto.setItem(itemMapper.toItemDto(comment.getItem()));
        commentDto.setAuthor(userMapper.toUserDto(comment.getAuthor()));

        return commentDto;
    }

    public static CommentInfoDto toCommentInfoDto(Comment comment) {

        CommentInfoDto commentInfoDto = new CommentInfoDto();
        commentInfoDto.setId(comment.getId());
        commentInfoDto.setText(comment.getText());
        commentInfoDto.setAuthorName(comment.getAuthor().getName());
        commentInfoDto.setCreated(comment.getCreated());

        return commentInfoDto;
    }

    public static List<CommentInfoDto> toCommentInfoDto(Iterable<Comment> comments) {
        List<CommentInfoDto> result = new ArrayList<>();

        for (Comment comment : comments) {
            result.add(toCommentInfoDto(comment));
        }

        return result;
    }

    public static List<CommentDto> toCommentDto(ItemMapper itemMapper,
                                         UserMapper userMapper,
                                         Iterable<Comment> comments) {
        List<CommentDto> result = new ArrayList<>();

        for (Comment comment : comments) {
            result.add(toCommentDto(itemMapper, userMapper, comment));
        }

        return result;
    }
}

