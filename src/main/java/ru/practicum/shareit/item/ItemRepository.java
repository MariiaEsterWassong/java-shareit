package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByOwnerIdOrderByIdAsc(Long userId);

    @Query("SELECT i FROM Item i " +
            "WHERE LOWER(i.name) LIKE CONCAT('%', LOWER(:text), '%') " +
            "OR LOWER(i.description) LIKE CONCAT('%', LOWER(:text), '%') " +
            "AND i.available = TRUE")
    List<Item> findItemsByTextOrderByIdAsc(String text);
}
