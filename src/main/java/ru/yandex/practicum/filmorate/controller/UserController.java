package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public Collection<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable int id) {
        return userService.getById(id);
    }

    @GetMapping("{id}/friends")
    public List<User> getFriendsListById(@PathVariable int id) {
        return userService.getFriendsListById(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriendsList(@PathVariable int id, @PathVariable int otherId) {
        return userService.getCommonFriendsList(id, otherId);
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping
    public User put(@Valid @RequestBody User user) {
        return userService.update(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public List<User> addFriend(@PathVariable int id, @PathVariable int friendId) {
        return userService.addFriendship(id, friendId);
    }

    @DeleteMapping("/{id}")
    public User deleteById(@PathVariable int id) {
        return userService.deleteById(id);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public List<User> removeFriend(@PathVariable int id, @PathVariable int friendId) {
        return userService.removeFriendship(id, friendId);
    }
}