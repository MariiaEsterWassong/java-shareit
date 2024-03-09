package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.IncorrectParameterException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.*;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;


    @Override
    public List<ItemDto> getAllUserItems(Long userId) {
        if (userRepository.getUser(userId) == null) {
            String msg = "Пользователь не найден.";
            log.error(msg);
            throw new IncorrectParameterException("owner");
        }
        List<Item> items = itemRepository.getAllUserItems(userId);
        return ItemMapper.toItemDto(items);
    }

    @Override
    public ItemDto getItem(Long id) {
        Item item = itemRepository.getItem(id);
        return ItemMapper.toItemDto(item);
    }

    @Override
    public ItemDto saveItem(Long userId, ItemDto itemDto) {
        if (userRepository.getUser(userId) == null) {
            String msg = "Пользователь не найден.";
            log.error(msg);
            throw new IncorrectParameterException("owner");
        }
        Item item = itemRepository.saveItem(ItemMapper.toItem(userId, itemDto));
        return ItemMapper.toItemDto(item);
    }

    @Override
    public ItemDto updateItem(Long userId, Long id, ItemDto itemDto) {
        if (userRepository.getUser(userId) == null) {
            String msg = "Пользователь не найден.";
            log.error(msg);
            throw new IncorrectParameterException("owner");
        }
        Item item = itemRepository.updateItem(userId, id, ItemMapper.toItem(id, itemDto));
        return ItemMapper.toItemDto(item);
    }


    @Override
    public void deleteItem(Long id) {
        itemRepository.deleteItem(id);
    }

    @Override
    public List<ItemDto> searchItemsByText(String text) {
        List<Item> items = itemRepository.searchItemsByText(text);
        return ItemMapper.toItemDto(items);
    }
}

