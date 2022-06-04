package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
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
    public User create(@Valid @RequestBody User user) throws RuntimeException {
        return userService.getUserStorage().create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) throws RuntimeException {
        return userService.getUserStorage().update(user);
    }

    @DeleteMapping
    public void remove(@Valid @RequestBody User user) throws RuntimeException {
        userService.getUserStorage().remove(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) throws RuntimeException {
        return userService.getUserStorage().getUserById(id);
    }

    @GetMapping
    public List<User> getAllUsers() throws RuntimeException {
        return userService.getUserStorage().getAllUsers();
    }



    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable long id, @PathVariable long friendId) throws RuntimeException {
        userService.addFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getAllFriends (@PathVariable Long id) throws RuntimeException {
        return userService.getAllFriends(id);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable Long id, @PathVariable Long friendId) throws RuntimeException {
        userService.removeFriend(id, friendId);
    }



    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> allCoincideFriends (@PathVariable Long id, @PathVariable Long otherId) throws RuntimeException {
        return userService.allCoincideFriends(id, otherId);
    }
}
