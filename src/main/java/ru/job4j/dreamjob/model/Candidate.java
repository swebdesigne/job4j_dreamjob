package ru.job4j.dreamjob.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class Candidate implements Serializable {
    private int id;
    private String name;
    private City city;
    private String description;
    private LocalDateTime created;
    private byte[] photo;


    public Candidate(int id, String name, String description, City city, byte[] photo, LocalDateTime created) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.city = city;
        this.photo = photo;
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
