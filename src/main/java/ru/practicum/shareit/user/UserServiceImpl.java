package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.IncorrectParameterException;
import ru.practicum.shareit.exception.NotFoundException;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = repository.findAll();
        return UserMapper.toUserDto(users);
    }

    @Override
    public UserDto getUser(Long id) {
        User user = repository.findById(id).orElseThrow(() ->
                new NotFoundException("Пользователь не найден"));
        return UserMapper.toUserDto(user);
    }


    @Override
    public UserDto saveUser(UserDto userDto) {

        User user = repository.save(UserMapper.toUser(userDto));
        return UserMapper.toUserDto(user);
    }

    private boolean ifEmailToSaveExist(String email) {
        List<User> users = repository.findAll();
        if (users != null && !users.isEmpty()) {
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {

        if (ifEmailToUpdateExist(id, userDto.getEmail())) {
            String msg = "Данная электронная почта зарезервирована за другим пользователем";
            log.error(msg);
            throw new IncorrectParameterException("email");
        }

        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }

        return UserMapper.toUserDto(repository.save(user));
    }

    private boolean ifEmailToUpdateExist(Long id, String email) {
        List<User> users = repository.findAll();
        if (users != null && !users.isEmpty()) {
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    if (!user.getId().equals(id)) {
                        return true;
                    }
                    return false;
                }
            }
        }
        return false;
    }

    public void deleteUser(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        repository.delete(user);
    }
}

