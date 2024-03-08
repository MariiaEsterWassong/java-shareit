package ru.practicum.shareit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.exception.IncorrectParameterException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.*;


@SpringBootTest
public class UserControllerTest {
    private UserController userController;

    @BeforeEach
    public void setUp() {
        userController = new UserController(new UserServiceImpl(new UserRepositoryImpl()));
    }

    @Test
    public void saveNewUser_shouldValidateUserAndAddToMap() {
        UserDto userDto = new UserDto();
        userDto.setName("user");
        userDto.setEmail("user@mail.com");
        userController.saveNewUser(userDto);
        Assertions.assertEquals(userDto.getEmail(), userController.getAllUsers().get(0).getEmail());

        UserDto userDtoWithSameEmail = new UserDto();
        userDtoWithSameEmail.setName("user");
        userDtoWithSameEmail.setEmail("user@mail.com");
        Assertions.assertThrows(IncorrectParameterException.class, () -> userController.saveNewUser(userDtoWithSameEmail));

        UserDto userDtoWithNoEmail = new UserDto();
        userDtoWithNoEmail.setName("user");
        userDtoWithNoEmail.setEmail("");
        Assertions.assertThrows(ValidationException.class, () -> userController.saveNewUser(userDtoWithNoEmail));

        UserDto userDtoWithWrongEmail = new UserDto();
        userDtoWithWrongEmail.setName("user");
        userDtoWithWrongEmail.setEmail("usermail.com");
        Assertions.assertThrows(ValidationException.class, () -> userController.saveNewUser(userDtoWithWrongEmail));
    }

    @Test
    public void updateUser_shouldValidateUserAndUpdate() {
        UserDto userDto = new UserDto();
        userDto.setName("user");
        userDto.setEmail("user@mail.com");
        UserDto userDtoSaved = userController.saveNewUser(userDto);
        Long userId = userDtoSaved.getId();
        UserDto updatedUserDto = new UserDto();
        updatedUserDto.setId(userId);
        updatedUserDto.setName("user");
        updatedUserDto.setEmail("newuser@mail.com");
        userController.updateUser(userId, updatedUserDto);
        Assertions.assertEquals(updatedUserDto, userController.getAllUsers().get((int) (userId - 1)));

    }
}
