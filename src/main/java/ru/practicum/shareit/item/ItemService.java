package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemInfoDto;

import java.util.List;

public interface ItemService {
    List<ItemInfoDto> getAllUserItems(Long userId);

    ItemDto saveItem(Long userId, ItemDto itemDto);

    ItemInfoDto getItem(Long userId, Long id);

    ItemDto updateItem(Long userId, Long id, ItemDto item);

    void deleteItem(Long id);

    List<ItemDto> searchItemsByText(String text);
}
