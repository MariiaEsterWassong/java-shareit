package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.CommentFromClientDto;
import ru.practicum.shareit.item.dto.CommentInfoDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemInfoDto;

import java.util.ArrayList;
import java.util.List;


/**
 * Controller class for handling operations related to items.
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/items")
public class ItemController {
    private final ItemService itemService;
    private final CommentService commentService;

    /**
     * Retrieves all items belonging to a user.
     *
     * @param userId The ID of the user whose items are to be retrieved.
     * @return A list of ItemDto objects representing the user's items.
     */

    @GetMapping
    public List<ItemInfoDto> getAllUserItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("GET /items");
        return itemService.getAllUserItems(userId);
    }

    /**
     * Retrieves a specific item by its ID.
     *
     * @param id The ID of the item to retrieve.
     * @return The ItemDto object representing the retrieved item.
     */
    @GetMapping("/{id}")
    public ItemInfoDto getItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                               @PathVariable("id") Long id) {
        log.info("GET /items/{id}");
        return itemService.getItem(userId, id);
    }

    /**
     * Saves a new item.
     *
     * @param userId The ID of the user creating the new item.
     * @param item   The ItemDto object representing the new item.
     * @return The ItemDto object representing the saved item.
     */
    @PostMapping
    public ItemDto saveNewItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                               @RequestBody ItemDto item) {
        log.info("POST /items");
        ValidationItemUtils.validateItemDto(item);
        return itemService.saveItem(userId, item);
    }

    /**
     * Updates an existing item.
     *
     * @param userId The ID of the user updating the item.
     * @param id     The ID of the item to update.
     * @param item   The ItemDto object representing the updated item.
     * @return The ItemDto object representing the updated item.
     */
    @PatchMapping("/{id}")
    public ItemDto updateItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                              @PathVariable("id") Long id,
                              @RequestBody ItemDto item) {
        log.info("Patch /items with RequestBody");
        return itemService.updateItem(userId, id, item);
    }

    /**
     * Deletes an item by its ID.
     *
     * @param id The ID of the item to delete.
     */
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable("id") Long id) {
        log.info("DELETE /items/{id}");
        itemService.deleteItem(id);
    }

    /**
     * Searches for items based on a text query.
     *
     * @param text The text query to search for.
     * @return A list of ItemDto objects matching the search query.
     */
    @GetMapping("/search")
    public List<ItemDto> searchItemsByText(@RequestParam String text) {
        log.info("GET /items/search");
        if (text.isEmpty()) {
            return new ArrayList<ItemDto>();
        }
        return itemService.searchItemsByText(text);
    }

    /**
     * Saves a new comment for the specified item.
     *
     * @param userId  The ID of the user making the comment.
     * @param itemId  The ID of the item for which the comment is being made.
     * @param comment The comment information provided by the client.
     * @return The information about the saved comment.
     * @throws ValidationException If the provided comment information is not valid.
     * @throws NotFoundException   If the specified item or user is not found.
     */
    @PostMapping("/{itemId}/comment")
    public CommentInfoDto saveNewComment(@RequestHeader("X-Sharer-User-Id") Long userId,
                                         @PathVariable Long itemId,
                                         @RequestBody CommentFromClientDto comment) {
        log.info("POST /items/{itemId}/comment");
        ValidationCommentUtils.validateCommentInfoDto(comment);
        return commentService.saveComment(userId, itemId, comment);
    }
}
