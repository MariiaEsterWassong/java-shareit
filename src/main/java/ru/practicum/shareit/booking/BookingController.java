package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingFromUserDto;

import java.util.List;

/**
 * Controller for handling booking-related HTTP requests.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {

    private final BookingService bookingService;

    /**
     * Saves a new booking.
     *
     * @param userId  The ID of the user making the booking.
     * @param booking The booking information provided by the client.
     * @return The information about the saved booking.
     */
    @PostMapping
    public BookingDto saveBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                  @RequestBody BookingFromUserDto booking) {
        log.info("POST /bookings");
        ValidationBookingUtils.validateBookingDto(booking);
        return bookingService.saveBooking(userId, booking);
    }

    /**
     * Updates the approval status of a booking.
     *
     * @param bookingId The ID of the booking to update.
     * @param userId    The ID of the user performing the update.
     * @param approved  The new approval status.
     * @return The updated booking information.
     */
    @PatchMapping("/{bookingId}")
    public BookingDto updateBookingApproval(@PathVariable Long bookingId,
                                            @RequestHeader("X-Sharer-User-Id") Long userId,
                                            @RequestParam(name = "approved") Boolean approved) {
        log.info("PATCH /bookings/:bookingId?approved=");
        return bookingService.updateBookingApproval(bookingId, userId, approved);
    }

    /**
     * Retrieves the details of a specific booking.
     *
     * @param bookingId The ID of the booking to retrieve.
     * @param userId    The ID of the user making the request.
     * @return The details of the requested booking.
     */
    @GetMapping("/{bookingId}")
    public BookingDto getBooking(@PathVariable Long bookingId,
                                 @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("GET /bookings/{id}");
        return bookingService.getBookingById(bookingId, userId);
    }

    /**
     * Retrieves a list of bookings for a specific booker.
     *
     * @param bookerId The ID of the booker.
     * @param state    The state of the bookings to retrieve (optional).
     * @return A list of bookings for the specified booker.
     */
    @GetMapping
    public List<BookingDto> getBookings(@RequestHeader("X-Sharer-User-Id") Long bookerId,
                                        @RequestParam(name = "state", defaultValue = "ALL") String state) {
        log.info("GET /bookings");
        return bookingService.getAllBookerBookings(bookerId, state);
    }

    /**
     * Retrieves a list of bookings for a specific owner.
     *
     * @param ownerId The ID of the owner.
     * @param state   The state of the bookings to retrieve (optional).
     * @return A list of bookings for the specified owner.
     */
    @GetMapping("/owner")
    public List<BookingDto> getOwnerBookings(@RequestHeader("X-Sharer-User-Id") Long ownerId,
                                             @RequestParam(name = "state", defaultValue = "ALL") String state) {
        log.info("GET /bookings/owner");
        return bookingService.getAllOwnerBookings(ownerId, state);
    }
}
