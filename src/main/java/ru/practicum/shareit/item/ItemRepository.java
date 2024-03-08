package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {

    List<Item> getAllUserItems(Long userId);

    Item getItem(Long id);

    Item saveItem(Item item);

    Item updateItem(Long userId, Long id, Item item);

    void deleteItem(Long id);

    List<Item> searchItemsByText(String text);

}
