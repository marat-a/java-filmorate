package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public abstract class Entity {
    int id;
    @NotBlank
    String name;
    public Entity(String name){
        this.name = name;
    }
}
