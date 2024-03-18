package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
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
    private static ItemRepository itemRepository;
    private static UserRepository userRepository;

    @Autowired
    public CommentMapper(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public static Comment toComment(Long authorId, Long itemId, CommentFromClientDto commentDto, LocalDateTime time) {

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

    public static CommentDto toCommentDto(Comment comment) {

        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        commentDto.setItem(ItemMapper.toItemDto(comment.getItem()));
        commentDto.setAuthor(UserMapper.toUserDto(comment.getAuthor()));

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

    public static List<CommentDto> toCommentDto(Iterable<Comment> comments) {
        List<CommentDto> result = new ArrayList<>();

        for (Comment comment : comments) {
            result.add(toCommentDto(comment));
        }

        return result;
    }
}

