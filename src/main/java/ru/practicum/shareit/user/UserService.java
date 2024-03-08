package ru.practicum.shareit.user;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();

    UserDto saveUser(UserDto userDto);

    UserDto getUser(Long id);


    UserDto updateUser(Long id, UserDto user);

    void deleteUser(Long id);
}
