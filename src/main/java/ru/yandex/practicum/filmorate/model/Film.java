package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Film {
    private int id;
    @NotNull
    @NotBlank
    private final String name;
    private final String description;
    @NotNull
    private final LocalDate releaseDate;
    @NotNull
    private final long duration;
}