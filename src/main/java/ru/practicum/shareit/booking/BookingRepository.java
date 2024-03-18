package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByBookerOrderByStartDesc(User booker);

    List<Booking> findAllByBookerAndStatusOrderByStartDesc(User booker, BookingState state);

    List<Booking> findAllByBookerAndStartIsAfterOrderByStartDesc(User booker, LocalDateTime now);

    List<Booking> findAllByBookerAndStatusAndEndIsBeforeOrderByStartDesc(
            User booker,
            BookingState state,
            LocalDateTime now
    );

    List<Booking> findAllByBookerAndStartIsBeforeAndEndIsAfterOrderByStartDesc(
            User booker,
            LocalDateTime nowToStart,
            LocalDateTime nowToEnd
    );

    List<Booking> findAllByItemIdInOrderByStartDesc(List<Long> itemIds);

    List<Booking> findAllByItemOrderByStartDesc(Item item);

    List<Booking> findByItemOwnerAndStatusOrderByStartDesc(User owner, BookingState state);

    List<Booking> findByItemOwnerAndStartBeforeAndEndAfterOrderByStartDesc(
            User owner,
            LocalDateTime nowToStart,
            LocalDateTime nowToEnd
    );

    List<Booking> findByItemOwnerAndStatusAndEndBeforeOrderByStartDesc(
            User owner,
            BookingState state,
            LocalDateTime now
    );

    List<Booking> findByItemOwnerAndStartAfterOrderByStartDesc(
            User owner,
            LocalDateTime now
    );

    List<Booking> findByBookerAndItemAndStartBeforeAndApproved(
            User booker,
            Item item,
            LocalDateTime now,
            Boolean approved
    );
}
