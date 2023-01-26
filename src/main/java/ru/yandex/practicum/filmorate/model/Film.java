package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.*;

import lombok.Data;
import ru.yandex.practicum.filmorate.validate.BeginOfCinemaEra;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {

    private Set<Integer> usersLikes = new HashSet<>();

    @PositiveOrZero
    private int id;

    @NotBlank(message = ".")
    private final String name;

    @NotBlank(message = ".")
    @Size(max=200, message ="Должно быть не больше 200 символов!")
    private final String description;

    @NotNull
    @BeginOfCinemaEra
    private final LocalDate releaseDate;

    @Min(value = 1, message = ".")
    @Positive
    private final long duration;

}