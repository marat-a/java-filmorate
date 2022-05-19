package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController extends Controller<Film> {

    @Override
    boolean validate(Film film) throws ValidationException {
        boolean isValidFilm;
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
        } else isValidFilm = true;
        return isValidFilm;
    }
}
