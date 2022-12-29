package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    protected Validator validator = factory.getValidator();
    private FilmController filmController;
    private Film film;

    @BeforeEach
    void filmControllerInit() {
        filmController = new FilmController();
    }

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        filmController = new FilmController();
    }

    @Test
    void releaseDateBefore1895() {
        film = new Film("1884", "some description", LocalDate.of(1884,1,1), 60);
        assertThrows(ValidationException.class, () -> filmController.validate(film));
    }

    @Test
    void duplicateFilmTest() {
        film = new Film("1984", "some description", LocalDate.of(1984,1,1), 60);
        film.setId(1);
        filmController.films.put(film.getId(), film);
        assertThrows(ValidationException.class, () -> filmController.validate(film));
    }

    @Test
    void emptyNameValidationTest() {
        film = new Film("", "some description", LocalDate.of(1999,1,1), 60);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    void balanceValidationTest() {
        film = new Film(" ", "some description", LocalDate.of(1999,1,1), 60);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    void nullNameValidationTest() {
        film = new Film(null, "some description", LocalDate.of(1999,1,1), 60);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    void emptyDescriptionTest() {
        film = new Film("1999", "", LocalDate.of(1999,1,1), 60);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    void balanseDescriptionTest() {
        film = new Film("1999", " ", LocalDate.of(1999,1,1), 60);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    void nullDescriptiomTest() {
        film = new Film("1999", null, LocalDate.of(1999,1,1), 60);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    void lengthDescriptionAbove200Test() {
        film = new Film("Брат","Осень 1996 года. Вернувшийся с Чеченской войны Данила Багров" +
                "гуляет по родному провинциальному городу и случайно срывает съёмки клипа" +
                "группы «Наутилус Помпилиус» на песню «Крылья». Данилу забирают в милицию за то," +
                " что он избил охранника съёмочной группы, но отпускают, пригрозив поставить на учёт," +
                " если он не устроится на работу. Мать ругает Данилу за участие в драке и во избежание повторения" +
                " криминальной судьбы отца советует ему ехать в Санкт-Петербург к старшему брату Виктору," +
                " который «там большой человек».",LocalDate.of(1997,12,12),95);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    void durationNotZeroTest() {
        film = new Film("1999", "some description", LocalDate.of(1999,1,1), 0);
        Set<ConstraintViolation<Film>> violations= validator.validate(film);
        assertFalse(violations.isEmpty());
        assertThat(violations.size()).isEqualTo(1);
    }

}