package ru.practicum.shareit.item.dto;

import lombok.*;
import ru.practicum.shareit.booking.dto.BookingInfoDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemInfoDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private List<CommentInfoDto> comments;
    private BookingInfoDto lastBooking;
    private BookingInfoDto nextBooking;

}
