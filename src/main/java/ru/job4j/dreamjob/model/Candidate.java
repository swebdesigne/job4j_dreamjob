package ru.job4j.dreamjob.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Candidate implements Serializable {
    private int id;
    private String name;
    private City city;
    private String description;
    private LocalDate created;
    private byte[] photo;


    public Candidate(int id, String name, String description, City city, byte[] photo, LocalDate created) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.city = city;
        this.photo = photo;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
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
