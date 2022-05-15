package ru.yandex.practicum.filmorate.model;

import jdk.jfr.DataAmount;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Film {
    int id;
    String name;
    String description;
    LocalDate releaseDate;
    int duration;
}