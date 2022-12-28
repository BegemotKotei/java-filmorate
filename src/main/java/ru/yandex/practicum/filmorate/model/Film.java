package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class Film {

    private int id;
    @NonNull
    @NotBlank
    private final String name;
    private final String description;
    @NonNull
    private final  LocalDate releaseDate;
    @NonNull
    private final long duration;
}