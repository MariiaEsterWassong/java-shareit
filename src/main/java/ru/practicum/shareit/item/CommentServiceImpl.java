package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.CommentFromClientDto;
import ru.practicum.shareit.item.dto.CommentInfoDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Override
    public CommentInfoDto saveComment(Long authorId, Long itemId, CommentFromClientDto commentDto) {
        User user = userRepository.findById(authorId).orElseThrow(() ->
                new NotFoundException("Пользователь не найден"));
        Item item = itemRepository.findById(itemId).orElseThrow(() ->
                new NotFoundException("Запрашиваемый обьект отсутствует в базе"));
        if (bookingRepository.findByBookerAndItemAndStartBeforeAndApproved(
                user,
                item,
                LocalDateTime.now(),
                true).isEmpty()) {
            throw new ValidationException("Не возможно оставить отзыв");
        } else {
            Comment comment = commentRepository.save(CommentMapper.toComment(authorId, itemId, commentDto, LocalDateTime.now()));
            return CommentMapper.toCommentInfoDto(comment);
        }
    }
}
