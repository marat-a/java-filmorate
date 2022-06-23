package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("InMemoryFilmStorage")
public class InMemoryFilmStorage implements FilmStorage{
    Map<Long, Film> filmStorageMap = new HashMap<>();


    @Override
    public void write(Film film) {
        if (filmStorageMap.containsKey(film.getId())){
            throw new ValidationException("Фильм с таким ID уже есть в хранилище");
        } else filmStorageMap.put(film.getId(), film);
    }

    @Override
    public void update(Film film) throws NotFoundException {
        if (!filmStorageMap.containsKey(film.getId())){
            throw new NotFoundException("Фильм с таким ID не найден");
        } else filmStorageMap.put(film.getId(), film);
    }

    @Override
    public void delete(Long filmId) throws NotFoundException {
        if (!filmStorageMap.containsKey(filmId)){
            throw new NotFoundException("Фильм с таким ID не найден");
        } else filmStorageMap.remove(filmId);
    }

    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(filmStorageMap.values());
    }

    @Override
    public Film getFilmById(Long filmId) throws NotFoundException {
        if (filmStorageMap.containsKey(filmId))
        return filmStorageMap.get(filmId);
        else throw new NotFoundException("Фильм с таким ID не найден");
    }



}
