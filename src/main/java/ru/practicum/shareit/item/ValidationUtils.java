package ru.practicum.shareit.item;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;

/**
 * Utility class for validating ItemDto objects.
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationUtils {

    /**
     * Validates an ItemDto object. Check if item name is provided, if item description is provided,
     * if item availability status is provided
     *
     * @param itemDto The ItemDto object to validate.
     * @throws ValidationException If any validation checks fail.
     */
    public static void validateItemDto(ItemDto itemDto) {

        if (itemDto.getName() == null || "".equals(itemDto.getName())) {
            String msg = "Не указано название вещи.";
            log.error(msg);
            throw new ValidationException(msg);
        }

        if (itemDto.getDescription() == null || "".equals(itemDto.getDescription())) {
            String msg = "Не указано описание вещи.";
            log.error(msg);
            throw new ValidationException(msg);
        }

        if (itemDto.isAvailable() == null) {
            String msg = "Не указан статус вещи.";
            log.error(msg);
            throw new ValidationException(msg);
        }
    }
}