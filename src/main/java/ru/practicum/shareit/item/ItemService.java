package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    List<ItemDto> getAllUserItems(Long userId);
    ItemDto saveItem(Long userId, ItemDto itemDto);
    ItemDto getItem(Long id);
    ItemDto updateItem(Long UserId, Long id, ItemDto item);
    void deleteItem(Long id);
    List<ItemDto> searchItemsByText(String text);
}
