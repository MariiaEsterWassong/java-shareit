package ru.practicum.shareit.user;

import java.util.List;

public interface UserRepository {

    List<User> getAllUsers();

    User getUser(Long id);

    User saveUser(User user);

    User updateUser(Long id, User user);

    void deleteUser(Long id);

}
