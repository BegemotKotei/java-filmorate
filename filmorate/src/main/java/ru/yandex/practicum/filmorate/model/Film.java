package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class Film {

    @NonNull
    private int id;
    @NonNull
    @NotBlank
    private String name;
    @NonNull
    private String description;
    @NonNull
    private LocalDate releaseDate;
    @NonNull
    private long duration;
}