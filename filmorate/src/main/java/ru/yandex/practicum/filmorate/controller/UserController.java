package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    private int userId = 1;
    private final Map<Integer, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        validate(user);
        checkUsers(user);
        users.put(userId++, user);
        log.info("Добавлен пользователь с логином {}", user.getLogin());
        return user;
    }

    @PutMapping
    public User put(@Valid @RequestBody User user) {
        validate(user);
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("Пользователь не зарегистрирован.");
        } else {
            users.remove(user.getId());
            checkUsers(user);
            users.put(user.getId(), user);
            log.info("Информация о пользователе {} обновлена", user.getLogin());
        }
        return user;
    }

    private void validate(@Valid @RequestBody User user) {
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ValidationException("логин не может быть пустым, содержать пробелы.");
        }
        if(user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if(user.getBirthDay().isAfter(LocalDate.now())) {
            throw new ValidationException("Marty McFly не балуйся=)");
        }
    }

    private void checkUsers(@RequestBody User user) {
        Collection<User> userCollection = users.values();
        for (User us : userCollection) {
            if (user.getLogin().equals(us.getLogin()) || user.getEmail().equals(us.getEmail())) {
                throw new ValidationException("Пользователь с таким email и login уже зарегистрирован.");
            }
        }
    }
}