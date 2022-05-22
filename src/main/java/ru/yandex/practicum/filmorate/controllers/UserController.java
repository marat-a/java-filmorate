package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@RestController
@RequestMapping("/users")
public class UserController extends Controller<User> {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Override
    boolean validate(User user) throws ValidationException {
        boolean isValidUser;
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            log.error("Неправильный е-мэйл: " + user.getEmail());
            throw new ValidationException("Неправильный е-мэйл");
        } else if (user.getLogin() == null || user.getLogin().contains(" ")) {
            log.error("Неправильный логин: " + user.getLogin());
            throw new ValidationException("Неправильный логин");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Неправильная дата рождения: " + user.getBirthday());
            throw new ValidationException("Неправильная дата рождения");
        } else isValidUser = true;

        if (user.getName()==null||user.getName().equals("")) {
            user.setName(user.getLogin());
        }
        return isValidUser;
    }
}
