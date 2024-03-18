package ru.practicum.shareit.item.dto;

import lombok.*;

import ru.practicum.shareit.user.UserDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentDto {
    private Long id;
    private String text;
    private ItemDto item;
    private UserDto author;
}
