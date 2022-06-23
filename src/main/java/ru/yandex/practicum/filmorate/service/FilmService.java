package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserService userService;
    private Long filmId = 0L;

    @Autowired
    public FilmService(@Qualifier("InMemoryFilmStorage") FilmStorage filmStorage,  @Lazy UserService userService) {
        this.filmStorage = filmStorage;
        this.userService = userService;
    }

    Long generateID (){
        return ++filmId;
    }

    boolean validate(Film film) throws ValidationException {
        if (film.getName() == null || film.getName().equals("")) {
            log.error("Название не может быть пустым" + film.getName());
            throw new ValidationException("Название не может быть пустым");
        } else if (film.getDescription().length() > 200 || film.getDescription().length() == 0) {
            log.error("неправильная длина описания " + film.getDescription().length());
            throw new ValidationException("неправильная длина описания");
        } else if (film.getReleaseDate().isBefore(LocalDate.parse("1895-12-28"))) {
            log.error("неправильная дата релиза " + film.getReleaseDate());
            throw new ValidationException("Неправильная дата релиза");
        } else if (film.getDuration() <= 0) {
            log.error("Длительность фильма должна быть положительной " + film.getDuration());
            throw new ValidationException("Длительность фильма должна быть положительной");
        } return true;
    }

    public Film createFilm(Film film) throws ValidationException {
        if (validate(film)){
            film.setId(generateID());
            filmStorage.write(film);
        }
        return film;
    }

    public Film updateFilm(Film film) throws ValidationException, NotFoundException {
        if (validate(film)){
            filmStorage.update(film);
        }
        return film;
    }

    public void deleteFilm(Film film) throws NotFoundException {
        filmStorage.delete(film.getId());

    }


    public void addLike(Long userId, Long filmId) throws NotFoundException {
        if (filmStorage.getFilmById(filmId) != null || userService.getUserById(userId) != null)
       filmStorage.getFilmById(filmId).getLikes().add(userId);
    }

    public void removeLike(Long filmId, Long userId) throws NotFoundException {
        if (!userService.getAllUsersID().contains(userId)){
            throw new NotFoundException("Пользователь с таким ID не найден");
        }
        filmStorage.getFilmById(filmId).getLikes().remove(userId);
    }

    public List<Film> getPopularFilms(Integer count) {
        if (count == null) {
            count = 10;
        }
        return filmStorage.getFilms()
                .stream()
                .sorted(Comparator.comparingInt(film -> film.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }

    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film getFilmById(Long id) throws NotFoundException {
        return filmStorage.getFilmById(id);
    }
}
