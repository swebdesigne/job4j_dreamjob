package ru.job4j.dreamjob.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class Post implements Serializable {
    private int id;
    private String name;
    private boolean visible;
    private City city;
    private String description;
    private LocalDateTime created;

    public Post() {
    }

    public Post(int id, String name, String description, City city, boolean visible, LocalDateTime created) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.city = city;
        this.visible = visible;
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
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
