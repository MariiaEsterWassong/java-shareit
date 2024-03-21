package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingFromUserDto;

import java.util.List;

public interface BookingService {

    BookingDto saveBooking(Long userId, BookingFromUserDto bookingDto);

    BookingDto updateBookingApproval(Long bookingId, Long userId, Boolean approved);

    BookingDto getBookingById(Long bookingId, Long userId);

    List<BookingDto> getAllBookerBookings(Long bookerId, String state);

    List<BookingDto> getAllOwnerBookings(Long ownerId, String state);
}
