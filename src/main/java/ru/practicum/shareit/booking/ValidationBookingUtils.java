package ru.practicum.shareit.booking;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.booking.dto.BookingFromUserDto;
import ru.practicum.shareit.exception.ValidationException;

import java.time.LocalDateTime;


@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationBookingUtils {

    public static void validateBookingDto(BookingFromUserDto bookingDto) {
        if (bookingDto.getStart() == null) {
            String msg = "Не верно указаны сроки бронирования.";
            log.error(msg);
            throw new ValidationException(msg);
        }

        if ((bookingDto.getEnd()) == null) {
            String msg = "Не верно указаны сроки бронирования.";
            log.error(msg);
            throw new ValidationException(msg);
        }

        if ((bookingDto.getStart()).equals(LocalDateTime.MIN)) {
            String msg = "Не верно указаны сроки бронирования.";
            log.error(msg);
            throw new ValidationException(msg);
        }

        if ((bookingDto.getEnd()).equals(LocalDateTime.MIN)) {
            String msg = "Не верно указаны сроки бронирования.";
            log.error(msg);
            throw new ValidationException(msg);
        }

        if ((bookingDto.getStart()).isAfter((bookingDto.getEnd()))) {
            String msg = "Не верно указаны сроки бронирования.";
            log.error(msg);
            throw new ValidationException(msg);
        }

        if ((bookingDto.getEnd()).isBefore(LocalDateTime.now())) {
            String msg = "Не верно указаны сроки бронирования.";
            log.error(msg);
            throw new ValidationException(msg);
        }

        if ((bookingDto.getStart()).isBefore(LocalDateTime.now())) {
            String msg = "Не верно указаны сроки бронирования.";
            log.error(msg);
            throw new ValidationException(msg);
        }

        if ((bookingDto.getStart()).isEqual((bookingDto.getEnd()))) {
            String msg = "Не верно указаны сроки бронирования.";
            log.error(msg);
            throw new ValidationException(msg);
        }

    }
}