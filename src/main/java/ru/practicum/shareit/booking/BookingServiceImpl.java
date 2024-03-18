package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingFromUserDto;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemMapper itemMapper;
    private final UserMapper userMapper;


    @Override
    public BookingDto saveBooking(Long bookerId, BookingFromUserDto bookingDto) {
        Booking booking = BookingMapper.toBooking(itemRepository, userRepository, bookerId, bookingDto);
        if ((booking.getItem().getOwner().getId()).equals(bookerId)) {
            throw new NotFoundException("Не возможно создать бронирование");
        }
        booking = bookingRepository.save(booking);
        return BookingMapper.toBookingDto(itemMapper, userMapper, booking);
    }

    @Override
    public BookingDto updateBookingApproval(Long bookingId, Long userId, Boolean approved) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() ->
                new NotFoundException("Бронирование не найдено"));
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Пользователь не найден"));
        Item item = itemRepository.findById(booking.getItem().getId()).orElseThrow(() ->
                new NotFoundException("Запрашиваемый обьект отсутствует в базе"));
        if (!item.getOwner().getId().equals(userId)) {
            throw new NotFoundException("Нет прав доступа на изменение обьекта");
        }
        if (booking.getStatus().equals(BookingState.APPROVED)) {
            throw new ValidationException("Бронирование уже подтвеждено");
        }
        if (approved && item.isAvailable()) {
            booking.setApproved(true);
            booking.setStatus(BookingState.APPROVED);
            booking = bookingRepository.save(booking);
        } else if (approved.equals(false)) {
            booking.setApproved(false);
            booking.setStatus(BookingState.REJECTED);
            booking = bookingRepository.save(booking);
        } else throw new ValidationException("Запрашиваемый обьект забронирован");

        return BookingMapper.toBookingDto(itemMapper, userMapper, booking);

    }

    @Override
    public BookingDto getBookingById(Long bookingId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Пользователь не найден"));
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() ->
                new NotFoundException("Бронирование не найдено"));
        if (!(booking.getBooker().getId().equals(userId) || booking.getItem().getOwner().getId().equals(userId))) {
            throw new NotFoundException("Нет прав доступа к бронированию");
        }
        return BookingMapper.toBookingDto(itemMapper, userMapper, booking);
    }

    @Override
    public List<BookingDto> getAllBookerBookings(Long bookerId, String state) {
        User booker = userRepository.findById(bookerId).orElseThrow(() ->
                new NotFoundException("Пользователь не найден"));
        List<Booking> bookings;
        switch (state) {
            case "ALL":
                bookings = bookingRepository.findAllByBookerOrderByStartDesc(booker);
                return BookingMapper.toBookingDto(itemMapper, userMapper, bookings);
            case "WAITING":
            case "REJECTED":
                bookings = bookingRepository.findAllByBookerAndStatusOrderByStartDesc(booker, BookingState.from(state));
                return BookingMapper.toBookingDto(itemMapper, userMapper, bookings);
            case "CURRENT":
                bookings = bookingRepository.findAllByBookerAndStartIsBeforeAndEndIsAfterOrderByStartDesc(booker, LocalDateTime.now(), LocalDateTime.now());
                return BookingMapper.toBookingDto(itemMapper, userMapper, bookings);
            case "PAST":
                bookings = bookingRepository.findAllByBookerAndStatusAndEndIsBeforeOrderByStartDesc(booker, BookingState.from("APPROVED"), LocalDateTime.now());
                return BookingMapper.toBookingDto(itemMapper, userMapper, bookings);
            case "FUTURE":
                bookings = bookingRepository.findAllByBookerAndStartIsAfterOrderByStartDesc(booker, LocalDateTime.now());
                return BookingMapper.toBookingDto(itemMapper, userMapper, bookings);
            default:
                throw new ValidationException("Unknown state: " + state);
        }
    }

    @Override
    public List<BookingDto> getAllOwnerBookings(Long ownerId, String state) {
        User owner = userRepository.findById(ownerId).orElseThrow(() ->
                new NotFoundException("Пользователь не найден"));
        List<Item> ownerItems = itemRepository.findAllByOwnerIdOrderByIdAsc(ownerId);
        List<Long> ownerItemsIds = ownerItems.stream()
                .map(Item::getId)
                .collect(Collectors.toList());
        List<Booking> bookings;
        switch (state) {
            case "ALL":
                bookings = bookingRepository.findAllByItemIdInOrderByStartDesc(ownerItemsIds);
                return BookingMapper.toBookingDto(itemMapper, userMapper, bookings);
            case "WAITING":
            case "REJECTED":
                bookings = bookingRepository.findByItemOwnerAndStatusOrderByStartDesc(owner, BookingState.from(state));
                return BookingMapper.toBookingDto(itemMapper, userMapper, bookings);
            case "CURRENT":
                bookings = bookingRepository.findByItemOwnerAndStartBeforeAndEndAfterOrderByStartDesc(
                        owner,
                        LocalDateTime.now(),
                        LocalDateTime.now());
                return BookingMapper.toBookingDto(itemMapper, userMapper, bookings);
            case "PAST":
                bookings = bookingRepository.findByItemOwnerAndStatusAndEndBeforeOrderByStartDesc(
                        owner,
                        BookingState.from("APPROVED"),
                        LocalDateTime.now());
                return BookingMapper.toBookingDto(itemMapper, userMapper, bookings);
            case "FUTURE":
                bookings = bookingRepository.findByItemOwnerAndStartAfterOrderByStartDesc(
                        owner,
                        LocalDateTime.now());
                return BookingMapper.toBookingDto(itemMapper, userMapper, bookings);
            default:
                throw new ValidationException("Unknown state: " + state);
        }
    }
}




