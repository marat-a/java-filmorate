package ru.yandex.practicum.filmorate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc

public class FilmControllerTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    void test1_shouldBeOkResponseWhenCreateFilmWithRightParameters() throws Exception {
        Film film = new Film("Мимино", "Отличный советский фильм", LocalDate.parse("1978-03-27"), 92);
        String body = mapper.writeValueAsString(film);
        this.mockMvc.perform(post("/films").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void test2_shouldNotBeOkResponseWhenCreateFilmWithWrongDate() throws Exception {
        Film film = new Film("Мимино", "Отличный советский фильм", LocalDate.parse("1894-03-27"), 92);        String body = mapper.writeValueAsString(film);
        this.mockMvc.perform(post("/films").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
    @Test
    void test3_shouldNotBeOkResponseWhenCreateFilmWithWrongDuration() throws Exception {
        Film film = new Film("Мимино", "Отличный советский фильм", LocalDate.parse("1978-03-27"), -1) ;       String body = mapper.writeValueAsString(film);
        this.mockMvc.perform(post("/films").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
    @Test
    void test10_shouldNotBeOkResponseWhenCreateFilmWithNullDuration() throws Exception {
        Film film = new Film("Мимино", "Отличный советский фильм", LocalDate.parse("1978-03-27"), 0) ;       String body = mapper.writeValueAsString(film);
        this.mockMvc.perform(post("/films").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
    @Test
    void test4_shouldNotBeOkResponseWhenCreateFilmWithWrongDescription() throws Exception {
        Film film = new Film("Мимино", "Отличный советский фильм, Отличный советский фильм," +
                "Отличный советский фильм,Отличный советский фильм,Отличный советский фильм,Отличный советский фильм, Отличный советский фильм,\" +\n" +
                "                \"Отличный советский фильм,Отличный советский фильм,Отличный советский фильм,"
                , LocalDate.parse("1978-03-27"), 92);
        String body = mapper.writeValueAsString(film);
        this.mockMvc.perform(post("/films").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
    @Test
    void test5_shouldNotBeOkResponseWhenCreateFilmWithWrongDescription() throws Exception {
        Film film = new Film("Мимино", "", LocalDate.parse("1978-03-27"), 92);
        String body = mapper.writeValueAsString(film);
        this.mockMvc.perform(post("/films").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
    @Test
    void test6_shouldNotBeOkResponseWhenCreateFilmWithWrongName() throws Exception {
        Film film = new Film("", "Отличный советский фильм", LocalDate.parse("1978-03-27"), 92) ;
        String body = mapper.writeValueAsString(film);
        this.mockMvc.perform(post("/films").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
    @Test
    void test7_shouldNotBeOkResponseWhenCreateFilmWithWrongName() throws Exception {
        Film film = new Film(null, "Отличный советский фильм", LocalDate.parse("1978-03-27"), 92) ;
        String body = mapper.writeValueAsString(film);
        this.mockMvc.perform(post("/films").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}

