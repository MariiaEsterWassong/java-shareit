package ru.practicum.shareit.item.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentInfoDto {
    private Long id;
    private String text;
    private String authorName;
    private LocalDateTime created;
}
