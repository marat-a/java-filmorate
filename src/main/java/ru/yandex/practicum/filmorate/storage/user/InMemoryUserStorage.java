package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("InMemoryUserStorage")
public class InMemoryUserStorage implements UserStorage {
    Map<Long, User> userStorageMap = new HashMap<>();

    @Override
    public void write(User user) {
        if (userStorageMap.containsKey(user.getId())){
            throw new ValidationException("Пользователь с таким ID уже есть в хранилище");
        } else userStorageMap.put(user.getId(), user);
    }

    @Override
    public void delete(Long userId) throws NotFoundException {
        if (!userStorageMap.containsKey(userId)){
            throw new NotFoundException("Пользователь с таким ID не найден");
        } else userStorageMap.remove(userId);
    }

    @Override
    public User getUserById(Long userId) throws NotFoundException {
        if (!userStorageMap.containsKey(userId)){
            throw new NotFoundException("Пользователь с таким ID не найден");
        }return userStorageMap.get(userId);
    }
    @Override
    public List<Long> getAllUsersId() {
        return new ArrayList<>(userStorageMap.keySet());
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(userStorageMap.values());
    }

    @Override
    public void update(User user) throws NotFoundException {
        if (!userStorageMap.containsKey(user.getId())){
            throw new NotFoundException("Пользователь с таким ID не найден");
        } else userStorageMap.put(user.getId(), user);
    }
}
