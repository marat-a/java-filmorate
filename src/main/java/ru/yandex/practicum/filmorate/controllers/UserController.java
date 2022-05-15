package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();
    private int id = 0;

    private int generateId() {
        while (users.containsKey(id)) {
            id++;
        }
        return id;
    }

    void logRequest(User user, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', переданный объект: '{}', Тело запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString(), user);
    }

    @GetMapping
    public Map<Integer, User> findAll() {
        return users;
    }

    @PostMapping
    public User create(@RequestBody User user, HttpServletRequest request) throws ValidationException {
        logRequest(user, request);
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            log.error("Неправильный е-мэйл: " + user.getEmail());
            throw new ValidationException("Неправильный е-мэйл");

        } else if (user.getLogin() == null || user.getLogin().contains("_")) {
            log.error("Неправильный логин: " + user.getLogin());
            throw new ValidationException("Неправильный логин");
        } else if (user.getBirthday().isAfter(LocalDate.now().plusDays(1))) {
            log.error("Неправильная дата рождения: " + user.getBirthday());
            throw new ValidationException("Неправильная дата рождения");
        } else if (user.getName() == null) {
            user.setName(user.getLogin());
        } else users.put(generateId(), user);
        log.info("Пользователь " + '\"' + user.getName() + '\"' + " зарегистрирован.");
        return users.get(user.getId());
    }

    @PutMapping
    public void update(@RequestBody User user, HttpServletRequest request) {
        logRequest(user, request);
        users.put(user.getId(), user);
    }
}
