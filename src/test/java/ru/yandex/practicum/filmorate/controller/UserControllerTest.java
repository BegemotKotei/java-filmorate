package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.time.LocalDate;
import java.util.Set;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    protected Validator validator = factory.getValidator();
    private UserController userController;
    private UserService userService;
    private UserStorage userStorage;
    private InMemoryUserStorage inMemoryUserStorage;
    private User user;

    @BeforeEach
    void UserControllerInit() {
        inMemoryUserStorage = new InMemoryUserStorage();
        userService = new UserService(inMemoryUserStorage);
        userController = new UserController(userService);
    }

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        userController = new UserController(userService);
        inMemoryUserStorage = new InMemoryUserStorage();
    }

    @Test
    void userWithoutNameTest() {
        user = new User("hard@test.ru","login", LocalDate.of(1999,7,10));
        inMemoryUserStorage.validate(user);
        assertEquals("login",user.getName());
        user = new User("hard@test.ru","login", LocalDate.of(1999,7,10));
        user.setName(" ");
        inMemoryUserStorage.validate(user);
        assertEquals("login",user.getName());
    }

    @Test
    void emailBlancTest() {
        user = new User(null,"login",LocalDate.of(1999,7,10));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertThat(violations.size()).isEqualTo(1);
        System.out.println(violations);
    }

    @Test
    void emailWithoutATTest() {
        user = new User("null", "login", LocalDate.of(1999,7,10));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertThat(violations.size()).isEqualTo(1);
        System.out.println(violations);
    }

    @Test
    void emailNullTest() {
        user = new User(null, "login", LocalDate.of(1999,7,10));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    void loginBlancTest() {
        user = new User("test@test.ru", " ", LocalDate.of(1999,7,10));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    void loginNullTest() {
        user = new User("test@test.ru", null, LocalDate.of(1999,7,10));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    void birthdateNullTest() {
        user = new User("test@test.ru", "login", null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    void birthdateIncorrectTest() {
        user = new User("test@test.ru", "login", LocalDate.of(2023,7,10));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertThat(violations.size()).isEqualTo(1);
    }
}