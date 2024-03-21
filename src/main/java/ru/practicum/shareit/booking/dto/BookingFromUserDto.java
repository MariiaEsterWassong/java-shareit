package ru.practicum.shareit.booking.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookingFromUserDto {
    private Long itemId;
    private LocalDateTime start;
    private LocalDateTime end;

    boolean isValidStartAfterEnd() {
        return start.isAfter(end);
    }
}
