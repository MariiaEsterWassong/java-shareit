package ru.practicum.shareit.booking;

public enum BookingState {
    WAITING, APPROVED, REJECTED, CANCELED;

    static BookingState from(String state) {
        for (BookingState value : BookingState.values()) {
            if (value.name().contentEquals(state)) {
                return value;
            }
        }
        return null;
    }
}
