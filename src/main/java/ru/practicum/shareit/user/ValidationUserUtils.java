package ru.practicum.shareit.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import ru.practicum.shareit.exception.ValidationException;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationUserUtils {

    /**
     * Validates a UserDto object before saving. Check if email is provided and has correct format.
     *
     * @param userDto The UserDto object to validate.
     * @throws ValidationException If the validation check fails.
     */
    public static void validateUserDtoSaved(UserDto userDto) {

        if ((userDto.getEmail() == null) || !userDto.getEmail().contains("@")) {
            String msg = "Электронная почта не указана или указана не верно";
            log.error(msg);
            throw new ValidationException(msg);
        }
    }
}