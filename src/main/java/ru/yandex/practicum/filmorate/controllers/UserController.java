package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) throws RuntimeException, ValidationException {
        return userService.createUser(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) throws RuntimeException, ValidationException, NotFoundException {
        return userService.updateUser(user);
    }

    @DeleteMapping
    public void remove(@Valid @RequestBody User user) throws NotFoundException {
        userService.removeUser(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) throws NotFoundException {
        return userService.getUserById(id);
    }

    @GetMapping
    public List<User> getAllUsers() throws RuntimeException {
        return userService.getAllUsers();
    }



    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@NotBlank @PathVariable long id, @NotBlank @PathVariable long friendId) throws NotFoundException {
        userService.addFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getAllFriends (@PathVariable Long id) throws RuntimeException, NotFoundException {
        return userService.getAllFriends(id);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable Long id, @PathVariable Long friendId) throws RuntimeException, ValidationException, NotFoundException {
        userService.removeFriend(id, friendId);
    }



    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> allCommonFriends (@PathVariable Long id, @PathVariable Long otherId) throws RuntimeException, NotFoundException {
        return userService.allCommonFriends(id, otherId);
    }
}
