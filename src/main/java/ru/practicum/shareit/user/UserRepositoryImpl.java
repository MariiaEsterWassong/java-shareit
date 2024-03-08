package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private Long generatorId = 0L;

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUser(Long id) {
        if (!users.containsKey(id)) {
            String msg = "Идентификатор отсутствует в базе";
            log.error(msg);
            throw new NotFoundException(msg);
        }
        return users.get(id);
    }

    @Override
    public User saveUser(User user) {
        user.setId(++generatorId);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(Long id, User user) {
        if ((id == null) || !users.containsKey(id)) {
            String msg = "Идентификатор не указан или отсутствует в базе";
            log.error(msg);
            throw new NotFoundException(msg);
        }
        User userSaved = users.get(id);
        if (user.getName() != null) {
            userSaved.setName(user.getName());
        }
        if (user.getEmail() != null) {
            userSaved.setEmail(user.getEmail());
        }

        return userSaved;
    }

    @Override
    public void deleteUser(Long id) {
        if (!users.containsKey(id)) {
            String msg = "Идентификатор отсутствует в базе";
            log.error(msg);
            throw new NotFoundException(msg);
        }
        users.remove(id);
    }

}

