package ru.job4j.dreamjob.store;

import jdk.jfr.Registered;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PostStore {
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final AtomicInteger inc = new AtomicInteger(3);

    private PostStore() {
        posts.put(1, new Post(1, "Junior Java Job", "Desc 1", LocalDateTime.now()));
        posts.put(2, new Post(2, "Middle Java Job", "Desc 2", LocalDateTime.now()));
        posts.put(3, new Post(3, "Senior Java Job", "Desc 3", LocalDateTime.now()));
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public void add(Post post) {
        post.setId(inc.incrementAndGet());
        posts.put(post.getId(), post);
    }

    public Object findById(int id) {
        return posts.get(id);
    }

    public void update(Post post) {
        posts.put(post.getId(), post);
    }
}
