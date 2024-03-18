package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemInfoDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItemMapper {

    private static UserRepository userRepository;

    @Autowired
    public ItemMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static ItemDto toItemDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.isAvailable());

        return itemDto;
    }

    public static ItemInfoDto toItemInfoDto(
            Item item, Booking
            lastBooking,
            Booking nextBooking,
            List<Comment> comments) {
        ItemInfoDto iteminfoDto = new ItemInfoDto();
        iteminfoDto.setId(item.getId());
        iteminfoDto.setName(item.getName());
        iteminfoDto.setDescription(item.getDescription());
        iteminfoDto.setAvailable(item.isAvailable());
        if (lastBooking != null) {
            iteminfoDto.setLastBooking(BookingMapper.toBookingInfoDto(lastBooking));
        }
        if (nextBooking != null) {
            iteminfoDto.setNextBooking(BookingMapper.toBookingInfoDto(nextBooking));
        }
        iteminfoDto.setComments(CommentMapper.toCommentInfoDto(comments));
        return iteminfoDto;

    }

    public static ItemInfoDto toItemInfoDto(Item item, List<Comment> comments) {
        ItemInfoDto iteminfoDto = new ItemInfoDto();
        iteminfoDto.setId(item.getId());
        iteminfoDto.setName(item.getName());
        iteminfoDto.setDescription(item.getDescription());
        iteminfoDto.setAvailable(item.isAvailable());
        iteminfoDto.setComments(CommentMapper.toCommentInfoDto(comments));
        return iteminfoDto;
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
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        item.setOwner(owner);

        return item;
    }
}





