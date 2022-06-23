package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Component
public interface FilmStorage {
    void write(Film film) throws RuntimeException;

    void update(Film film) throws NotFoundException;

    void delete(Long film) throws RuntimeException, NotFoundException;

    List<Film> getFilms() throws RuntimeException;

    Film getFilmById(Long id) throws RuntimeException, NotFoundException;

}

