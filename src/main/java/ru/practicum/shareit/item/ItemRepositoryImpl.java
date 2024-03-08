package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Long, Item> items = new HashMap<>();
    private Long generatorId = 0L;

    
    @Override
    public List<Item> getAllUserItems(Long userId) {
        List<Item> userItems = new ArrayList<>();
        for (Item item : items.values()) {
            if (item.getOwner() == userId) {
                userItems.add(item);
            }
        }
        return userItems;
    }

    @Override
    public Item getItem(Long id) {
        if (!items.containsKey(id)) {
            String msg = "Идентификатор отсутствует в базе";
            log.error(msg);
            throw new NotFoundException(msg);
        }
        return items.get(id);
    }

    @Override
    public Item saveItem(Item item) {
        item.setId(++generatorId);
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Item updateItem(Long userId, Long id, Item item) {
        if ((id == null) || !items.containsKey(id)) {
            String msg = "Идентификатор не указан или отсутствует в базе";
            log.error(msg);
            throw new NotFoundException(msg);
        }
        if (items.get(id).getOwner() != userId) {
            String msg = "У данного пользователя нет права доступа редактировать данный обьект";
            log.error(msg);
            throw new NotFoundException(msg);
        }
        Item itemSaved = items.get(id);
        if (item.getName() != null) {
            itemSaved.setName(item.getName());
        }
        if (item.getDescription() != null) {
            itemSaved.setDescription(item.getDescription());
        }
        if (item.isAvailable() != null) {
            itemSaved.setAvailable(item.isAvailable());
        }

        return itemSaved;
    }

    @Override
    public void deleteItem(Long id) {
        if (!items.containsKey(id)) {
            String msg = "Идентификатор отсутствует в базе";
            log.error(msg);
            throw new NotFoundException(msg);
        }
        items.remove(id);
    }

    @Override
    public List<Item> searchItemsByText(String text) {
        List<Item> foundItems = new ArrayList<>();
        for (Item item : items.values()) {
            if (item.getName().toLowerCase().contains(text.toLowerCase()) ||
                    item.getDescription().toLowerCase().contains(text.toLowerCase()) &&
                            Boolean.TRUE.equals(item.isAvailable())) {
                foundItems.add(item);
            }
        }
        return foundItems;
    }
}

