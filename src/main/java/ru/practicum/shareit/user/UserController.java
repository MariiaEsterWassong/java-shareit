package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Controller class for handling operations related to users.
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    /**
     * Retrieves all users.
     *
     * @return A list of UserDto objects representing all users.
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        log.info("GET /users");
        return userService.getAllUsers();
    }

    /**
     * Retrieves a specific user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The UserDto object representing the retrieved user.
     */
    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") Long id) {
        log.info("GET /users/{id}");
        return userService.getUser(id);
    }

    /**
     * Saves a new user.
     *
     * @param user The UserDto object representing the new user.
     * @return The UserDto object representing the saved user.
     */
    @PostMapping
    public UserDto saveNewUser(@RequestBody UserDto user) {
        log.info("POST /users");
        ValidationUtils.validateUserDtoSaved(user);
        return userService.saveUser(user);
    }

    /**
     * Updates an existing user.
     *
     * @param id   The ID of the user to update.
     * @param user The UserDto object representing the updated user.
     * @return The UserDto object representing the updated user.
     */
    @PatchMapping("/{id}")
    public UserDto updateUser(@PathVariable("id") Long id, @RequestBody UserDto user) {
        log.info("Patch /users with RequestBody");
        return userService.updateUser(id, user);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to delete.
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        log.info("DELETE /users/{id}");
        userService.deleteUser(id);
    }
}
