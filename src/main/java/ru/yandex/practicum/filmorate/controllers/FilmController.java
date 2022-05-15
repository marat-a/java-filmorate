package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping ("/films")
public class FilmController {
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private final Map<Integer, Film> films = new HashMap<>();
    private int id= 0;

    private int generateId(){
        while (films.containsKey(id)){
            id++;
        }
        return id;
    }

    @GetMapping
    public Map<Integer, Film> findAll() {
        return films;
    }

    @PostMapping
    public Film create(@RequestBody Film film) throws ValidationException {
        if (film.getName()== null||film.getName().equals("")) {
            throw new ValidationException("Название не может быть пустым");
        } else if (film.getDescription().length() >200||film.getDescription().length() ==0) {
            throw new ValidationException("неправильная длина описания");
        } else if (film.getReleaseDate().isBefore(LocalDate.parse("1895-12-28"))) {
            throw new ValidationException("Неправильная дата релиза");
        } else if (film.getDuration()<=0) {
            throw new ValidationException("Длительность фильма должна быть положительной");
        } else films.put(generateId(), film);
        return films.get(film.getId());
    }

    @PutMapping
    public void update(@RequestBody Film film) {
        films.put(film.getId(), film);
    }
}
