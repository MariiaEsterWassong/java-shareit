package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemMapper {
    public static ItemDto toItemDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.isAvailable());

        return itemDto;
    }

    public static List<ItemDto> toItemDto(Iterable<Item> items) {
        List<ItemDto> result = new ArrayList<>();

        for (Item item : items) {
            result.add(toItemDto(item));
        }

        return result;
    }

    public static Item toItem(Long userId, ItemDto itemDto) {
        Item item = new Item();
        item.setId(itemDto.getId());
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.isAvailable());
        item.setOwner(userId);

        return item;
    }

    public static Item toItem(Long userId, Long requestId, ItemDto itemDto) {
        Item item = new Item();
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(item.isAvailable());
        item.setOwner(userId);
        item.setRequest(requestId);

        return item;
    }
}





