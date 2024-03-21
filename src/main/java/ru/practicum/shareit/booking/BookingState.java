package ru.practicum.shareit.booking;

import java.util.Arrays;

public enum BookingState {
    WAITING, APPROVED, REJECTED, CANCELED;

    static BookingState from(String state) {
        return Arrays.stream(BookingState.values())
                .filter(value -> value.name().equals(state))
                .findFirst()
                .orElse(null);
    }
}
