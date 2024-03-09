package ru.practicum.shareit.item.dto;

import lombok.Data;

/**
 * TODO Sprint add-controllers.
 */

@Data
public class ItemDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;

    public ItemDto() {
    }

    public Boolean isAvailable() {
        return available;
    }
}
