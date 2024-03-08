package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.IncorrectParameterException;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = repository.getAllUsers();
        return UserMapper.toUserDto(users);
    }

    @Override
    public UserDto getUser(Long id) {
        User user = repository.getUser(id);
        return UserMapper.toUserDto(user);
    }


    @Override
    public UserDto saveUser(UserDto userDto) {
        if (ifEmailToSaveExist(userDto.getEmail())) {
            String msg = "Данная электронная почта зарезервирована за другим пользователем";
            log.error(msg);
            throw new IncorrectParameterException("email");
        }
        User user = repository.saveUser(UserMapper.toUser(userDto));
        return UserMapper.toUserDto(user);
    }

    private boolean ifEmailToSaveExist(String email) {
        List<User> users = repository.getAllUsers();
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
        User user = repository.updateUser(id, UserMapper.toUser(userDto));
        return UserMapper.toUserDto(user);
    }

    private boolean ifEmailToUpdateExist(Long id, String email) {
        List<User> users = repository.getAllUsers();
        if (users != null && !users.isEmpty()) {
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    if (user.getId() != id) {
                        return true;
                    }
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteUser(id);
    }
}

