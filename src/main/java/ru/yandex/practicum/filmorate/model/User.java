package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
public class User {
    Long id;
    String name;

    @Email
    String email;

    @Pattern(regexp = "\\S*$")
    String login;

    public User(String name, String email, String login, LocalDate birthday) {
        this.name = name;
        this.email = email;
        this.login = login;
        this.birthday = birthday;
    }

    LocalDate birthday;

    Set<Long> friends = new HashSet<>();


}
