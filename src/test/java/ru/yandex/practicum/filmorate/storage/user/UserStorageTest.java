package ru.yandex.practicum.filmorate.storage.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.InternalException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserStorageTest {
    private InMemoryUserStorage userStorage;
    private User user;

    @BeforeEach
    void init() {
        userStorage = new InMemoryUserStorage();
    }

    @Test
    void userWithoutNameTest() {
        user = new User("tets@test.com", "login", LocalDate.of(1999, 1, 2));
        userStorage.validate(user);
        assertEquals("login",user.getName());

        user = new User("tets@test.com", "login", LocalDate.of(1999, 1, 2));
        user.setName(" ");
        userStorage.validate(user);
        assertEquals("login", user.getName());
    }

    @Test
    void duplicateUserTest() {
        user = new User("tets@test.com", "login", LocalDate.of(1999, 1, 2));
        user.setId(1);
        userStorage.getUsers().put(user.getId(), user);
        assertThrows(InternalException.class, () -> userStorage.checkUsers(user));
    }

}