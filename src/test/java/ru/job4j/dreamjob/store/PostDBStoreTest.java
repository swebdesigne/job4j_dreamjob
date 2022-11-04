package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.AfterClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PostDBStoreTest {
    private static final BasicDataSource POOL = new Main().loadPool();
    private final PostDBStore store = new PostDBStore(POOL);

    @AfterEach
    public void wipeTable() throws SQLException {
        try (Connection cn = POOL.getConnection();
             PreparedStatement ps = cn.prepareStatement("TRUNCATE table Post")
        ) {
            ps.execute();
        }
    }

    @AfterClass
    public static void closeCnPool() throws SQLException {
        POOL.close();
    }

    @Test
    public void whenCreatePost() {
        Post post = new Post(
                0, "Java Job", "Spring Boot", new City(0, ""), true, LocalDateTime.now()
        );
        store.add(post);
        Post postFInDb = store.findById(post.getId());
        assertThat(postFInDb.getName()).isEqualTo(post.getName());
    }

    @Test
    public void whenFindAllPosts() {
        Post post = new Post(
                0, "Java Job", "Spring Boot", new City(0, ""), true, LocalDateTime.now()
        );
        List<Post> listPost = List.of(
                post,
                new Post(
                        1, "C++", "Qt", new City(1, ""), true, LocalDateTime.now()
                )
        );
        listPost.forEach(store::add);
        List<Post> getPosts = store.findAll();
        assertThat(getPosts).isEqualTo(listPost);
    }

    @Test
    public void whenUpdatePost() {
        Post post = new Post(
                0, "Java Job", "Spring Boot", new City(0, ""), true, LocalDateTime.now()
        );
        store.add(post);
        Post newPost = new Post(
                post.getId(), "C++", "Qt", new City(2, ""), true, LocalDateTime.now()
        );
        store.update(newPost);
        Post findIdPost = store.findById(post.getId());
        assertThat(findIdPost.getName()).isEqualTo(newPost.getName());
    }
}