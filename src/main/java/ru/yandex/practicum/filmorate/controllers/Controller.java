package ru.yandex.practicum.filmorate.controllers;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Entity;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@Getter
abstract public class Controller<T extends Entity> {
    final Map<Integer, T> map = new HashMap<>();
    int id = 1;

    int generateId() {
        while (map.containsKey(id)) {
            id++;
        }
        return id;
    }

    void logRequest(T body, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', переданный объект: '{}', Тело запроса: '{}'", request.getMethod(), request.getRequestURI(), request.getQueryString(), body);
    }

    abstract boolean validate(T object) throws ValidationException;

    @GetMapping
    public Map<Integer, T> findAll() {
        return map;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody T object, HttpServletRequest request) {

        logRequest(object, request);
        try {
            if (validate(object)) {
                object.setId(generateId());
                map.put(object.getId(), object);
                log.info(object.getClass().getSimpleName()+ " " + '\"' + object.getName() + '\"' + " зарегистрирован.");
                return new ResponseEntity<>(map.get(object.getId()), HttpStatus.OK);
            }
        } catch (ValidationException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody T object, HttpServletRequest request)  {
        logRequest(object, request);
        try {
            if (validate(object)){
                map.put(object.getId(), object);
                log.info(object.getClass().getSimpleName()+ " " + '\"' + object.getName() + '\"' + " обновлен.");
                return new ResponseEntity<T>(HttpStatus.OK);
            } else return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (ValidationException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
