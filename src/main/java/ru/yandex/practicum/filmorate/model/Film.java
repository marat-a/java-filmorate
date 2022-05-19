package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@ToString
public class Film extends Entity{

    @Size(min=1, max=200)
    String description;

    LocalDate releaseDate;

    @Min(1)
    int duration;

    public Film(String name, String description, LocalDate releaseDate, int duration) {
        super(name);
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}