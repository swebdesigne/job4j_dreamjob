package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ThreadSafe
@Repository
public class PostDBStore {
    private final static String FIND_ALL_QUERY = "SELECT * FROM Post";
    private final static String ADD_QUERY = "INSERT INTO Post (name, description, city_id, visible, created) values (?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM Post WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE Post SET name = ?, description = ?, city_id = ?, visible = ?, created = ? WHERE id = ?";
    private static final Logger LOGGER = LoggerFactory.getLogger(PostDBStore.class.getSimpleName());
    private final BasicDataSource pool;

    public PostDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Post> findAll() {
        LOGGER.info("Получение всех вакансий");
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_ALL_QUERY)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(createPost(it));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Ошибка получения всех вакансий:", e.getCause());
        }
        return posts;
    }

    public Post add(Post post) {
        LOGGER.info("Добавление вакансии");
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(ADD_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setInt(3, post.getCity().getId());
            ps.setBoolean(4, post.isVisible());
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Ошибка добавления вакансии:", e.getCause());
        }
        return post;
    }

    public Post findById(int id) {
        LOGGER.info("Получение вакансии по id");
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID_QUERY)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return createPost(it);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Ошибка получения вакансии по id:", e.getCause());
        }
        return null;
    }

    public boolean update(Post post) {
        LOGGER.info("Обновление вакансии");
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(UPDATE_QUERY)
        ) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setInt(3, post.getCity().getId());
            ps.setBoolean(4, post.isVisible());
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(6, post.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error("Ошибка обновления вакансии:", e.getCause());
        }
        return false;
    }

    private Post createPost(ResultSet it) throws SQLException {
        return new Post(
                it.getInt("id"),
                it.getString("name"),
                it.getString("description"),
                new City(it.getInt("city_id"), ""),
                it.getBoolean("visible"),
                it.getTimestamp("created").toLocalDateTime()
        );
    }

}
