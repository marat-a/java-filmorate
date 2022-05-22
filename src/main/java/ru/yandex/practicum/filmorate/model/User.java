package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class User extends Entity{

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


}
