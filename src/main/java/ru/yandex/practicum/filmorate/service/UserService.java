package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Service
public class UserService {

    private final FilmService filmService;
    private final UserStorage userStorage;
    private Long userId = 0L;

    @Autowired
    public UserService(@Qualifier("InMemoryUserStorage") UserStorage userStorage, FilmService filmService) {
        this.filmService = filmService;
        this.userStorage = userStorage;
    }

    Long generateID() {
        return ++userId;
    }

    boolean validate(User user) throws ValidationException {
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            log.error("Неправильный е-мэйл: " + user.getEmail());
            throw new ValidationException("Неправильный е-мэйл");
        } else if (user.getLogin() == null || user.getLogin().contains(" ")) {
            log.error("Неправильный логин: " + user.getLogin());
            throw new ValidationException("Неправильный логин");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Неправильная дата рождения: " + user.getBirthday());
            throw new ValidationException("Неправильная дата рождения");
        }
        if (user.getName() == null || user.getName().equals("")) {
            user.setName(user.getLogin());
        }
        return true;
    }

    public User createUser(User user) throws ValidationException {
        if (validate(user)) {
            user.setId(generateID());
            userStorage.write(user);
        }
        return user;
    }

    public User updateUser(User user) throws ValidationException, NotFoundException {
        if (validate(user)) {
            userStorage.update(user);
        }
        return user;
    }

    public void removeUser(User user) throws NotFoundException {
        userStorage.delete(user.getId());
    }

    public User getUserById(long id) throws NotFoundException {
        return userStorage.getUserById(id);
    }

    public List<User> getAllUsers() {
        return userStorage.getUsers();
    }
    public List<Long> getAllUsersID() {
        return userStorage.getAllUsersId();
    }

    public void addFriend(Long id, Long friendId) throws  NotFoundException {
        if(!id.equals(friendId)
                && userStorage.getAllUsersId().contains(id)
                && userStorage.getAllUsersId().contains(friendId)){
            userStorage.getUserById(id).getFriends().add(friendId);
            userStorage.getUserById(friendId).getFriends().add(id);
        }
        else throw new NotFoundException("Указан несуществующий ID");
    }

    public void removeFriend(Long id, Long friendId) throws ValidationException, NotFoundException {
        if (userStorage.getUserById(id).getFriends().contains(friendId)
                &&userStorage.getUserById(friendId).getFriends().contains(id)){
            userStorage.getUserById(id).getFriends().remove(friendId);
            userStorage.getUserById(friendId).getFriends().remove(id);
        }
        else throw new ValidationException("Указан несуществующий друг");
    }

    public List<User> getAllFriends(Long id) throws NotFoundException {
        List<User> friendsList = new ArrayList<>();
        for (Long friend : userStorage.getUserById(id).getFriends()) {
            friendsList.add(userStorage.getUserById(friend));
        }
        return friendsList;
    }

    public List<User> allCommonFriends(Long id, Long otherId) throws NotFoundException {
        List<User> commonFriends = new ArrayList<>();
        for (Long friend : userStorage.getUserById(id).getFriends()) {
            if (userStorage.getUserById(otherId).getFriends().contains(friend)) {
                commonFriends.add(userStorage.getUserById(friend));
            }
        }
        return commonFriends;
    }


}
