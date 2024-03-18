package ru.practicum.shareit.item;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.CommentFromClientDto;

/**
 * Utility class for validating ItemDto objects.
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationCommentUtils {

    public static void validateCommentInfoDto(CommentFromClientDto commentFromClientDto) {
        if (commentFromClientDto.getText().isEmpty()) {
            String msg = "Не указан текс комментария";
            log.error(msg);
            throw new ValidationException(msg);
        }
    }
}