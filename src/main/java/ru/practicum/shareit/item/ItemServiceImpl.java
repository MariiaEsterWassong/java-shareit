package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.exception.ForbiddenException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemInfoDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final BookingMapper bookingMapper;
    private final CommentMapper commentMapper;


    @Override
    public List<ItemInfoDto> getAllUserItems(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Пользователь не найден"));

        List<Item> items = itemRepository.findAllByOwnerIdOrderByIdAsc(userId);
        List<ItemInfoDto> itemsInfoDto = new ArrayList<>();


        for (Item item : items) {
            List<Booking> bookings = bookingRepository.findAllByItemOrderByStartDesc(item);
            Booking lastBooking = getLastBooking(bookings);
            Booking nextBooking = getNextBooking(bookings);
            List<Comment> comments = commentRepository.findAllByItemOrderByIdAsc(item);
            itemsInfoDto.add(ItemMapper.toItemInfoDto(
                    bookingMapper,
                    commentMapper,
                    item,
                    lastBooking,
                    nextBooking,
                    comments));
        }
        return itemsInfoDto;
    }

    @Override
    public ItemInfoDto getItem(Long userId, Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Запрашиваемый обьект отсутствует в базе"));
        List<Booking> bookings = bookingRepository.findAllByItemOrderByStartDesc(item);
        Booking lastBooking = getLastBooking(bookings);
        Booking nextBooking = getNextBooking(bookings);
        List<Comment> comments = commentRepository.findAllByItemOrderByIdAsc(item);
        if (!item.getOwner().getId().equals(userId)) {
            return ItemMapper.toItemInfoDto(commentMapper, item, comments);
        }
        return ItemMapper.toItemInfoDto(bookingMapper, commentMapper, item, lastBooking, nextBooking, comments);
    }

    @Override
    public ItemDto saveItem(Long userId, ItemDto itemDto) {

        Item item = itemRepository.save(ItemMapper.toItem(userRepository, userId, itemDto));
        return ItemMapper.toItemDto(item);
    }

    @Override
    public ItemDto updateItem(Long userId, Long id, ItemDto itemDto) {

        Item item = itemRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Запрашиваемый обьект отсутствует в базе"));
        if (!item.getOwner().getId().equals(userId)) {
            throw new ForbiddenException("Нет прав доступа на изменение обьекта");
        }
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.isAvailable() != null) {
            item.setAvailable(itemDto.isAvailable());
        }

        return ItemMapper.toItemDto(itemRepository.save(item));
    }

    @Override
    public void deleteItem(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Запрашиваемый обьект отсутствует в базе"));
        itemRepository.delete(item);
    }

    @Override
    public List<ItemDto> searchItemsByText(String text) {
        List<Item> items = itemRepository.findItemsByTextOrderByIdAsc(text);
        return ItemMapper.toItemDto(items);
    }

    private Booking getLastBooking(List<Booking> bookings) {
        Booking lastBooking = null;
        for (Booking booking : bookings) {
            if (booking.getStart().isBefore(LocalDateTime.now())) {
                lastBooking = booking;
                break;
            }
        }
        return lastBooking;
    }

    private Booking getNextBooking(List<Booking> bookings) {
        Booking nextBooking = null;
        for (Booking booking : bookings) {

            if (booking.getStart().isAfter(LocalDateTime.now()) && booking.getApproved()) {
                if (nextBooking == null || booking.getStart().isBefore(nextBooking.getStart())) {
                    nextBooking = booking;
                }
            }
        }
        return nextBooking;
    }
}

