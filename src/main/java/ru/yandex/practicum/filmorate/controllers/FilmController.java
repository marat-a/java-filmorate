package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) throws RuntimeException {
        return filmService.getFilmStorage().create(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) throws RuntimeException {
        return filmService.getFilmStorage().update(film);
    }

    @DeleteMapping
    public void deleteFilm(@Valid @RequestBody Film film) throws RuntimeException {
        filmService.getFilmStorage().delete(film);
    }

    @GetMapping
    public List<Film> getAllFilms() throws RuntimeException {
        return filmService.getFilmStorage().getFilms();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Long id) throws RuntimeException {
        return filmService.getFilmStorage().getFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Long id, @PathVariable Long userId) throws RuntimeException {
        filmService.addLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms
            (@RequestParam (required = false) Long count) throws RuntimeException {
        return filmService.getPopularFilms(count);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public void removeLike(@PathVariable Long filmId, @PathVariable Long userId) throws RuntimeException {
        filmService.removeLike(filmId, userId);
    }


}

