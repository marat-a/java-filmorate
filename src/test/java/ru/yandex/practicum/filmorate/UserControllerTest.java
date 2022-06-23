package ru.yandex.practicum.filmorate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc

public class UserControllerTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    void test1_shouldBeOkResponseWhenCreateUserWithRightParameters() throws Exception {
        User user = new User("Oleg", "asd@asd.ru", "oleg123", LocalDate.parse("1982-07-07"));
        String body = mapper.writeValueAsString(user);
        this.mockMvc.perform(post("/users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void test2_shouldNotBeOkResponseWhenCreateUserWithFutureDate() throws Exception {
        User user = new User("Oleg", "asd@asd.ru", "oleg123", LocalDate.now().plusDays(1));
        String body = mapper.writeValueAsString(user);
        this.mockMvc.perform(post("/users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
    @Test
    void test3_shouldNotBeOkResponseWhenCreateUserWithWrongEmail() throws Exception {
        User user = new User("Oleg", "asdasd.ru", "oleg123", LocalDate.parse("1982-07-07"));
        String body = mapper.writeValueAsString(user);
        this.mockMvc.perform(post("/users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
    @Test
    void test4_shouldNotBeOkResponseWhenCreateUserWitNullEmail() throws Exception {
        User user = new User("Oleg", null, "oleg123", LocalDate.parse("1982-07-07"));
        String body = mapper.writeValueAsString(user);
        this.mockMvc.perform(post("/users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
    @Test
    void test5_shouldNotBeOkResponseWhenCreateUserWithBlankEmail() throws Exception {
        User user = new User("Oleg", "", "oleg123", LocalDate.parse("1982-07-07"));
        String body = mapper.writeValueAsString(user);
        this.mockMvc.perform(post("/users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
    @Test
    void test6_shouldNotBeOkResponseWhenCreateUserWithWrongLogin() throws Exception {
        User user = new User("Oleg", "asd@asd.ru", "oleg 123", LocalDate.parse("1982-07-07"));
        String body = mapper.writeValueAsString(user);
        this.mockMvc.perform(post("/users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void test8_shouldNotBeOkResponseWhenCreateUserWitNullLogin() throws Exception {
        User user = new User("Oleg", "asd@asd.ru", null, LocalDate.parse("1982-07-07"));
        String body = mapper.writeValueAsString(user);
        this.mockMvc.perform(post("/users").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }


}

