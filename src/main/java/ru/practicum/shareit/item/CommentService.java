package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.CommentFromClientDto;
import ru.practicum.shareit.item.dto.CommentInfoDto;

public interface CommentService {
    CommentInfoDto saveComment(Long userId, Long itemId, CommentFromClientDto commentDto);
}
