package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Component
public interface UserStorage {

    User getUserById(Long userId) throws NotFoundException;
    void write(User user);
    void delete(Long userId) throws NotFoundException;

    List<Long> getAllUsersId();

    List<User> getUsers();

    void update(User user) throws NotFoundException;
}
