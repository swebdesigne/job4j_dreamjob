package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@ThreadSafe
@Repository
public class UserDBStore {
    private final BasicDataSource pool;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDBStore.class.getSimpleName());
    private final static String ADD_QUERY = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

    public UserDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<User> add(User user) {
        LOGGER.info("Добавление пользователя");
        try (Connection cn = pool.getConnection();
             PreparedStatement sp = cn.prepareStatement(
                     ADD_QUERY, PreparedStatement.RETURN_GENERATED_KEYS
             )
        ) {
            sp.setString(1, user.getName());
            sp.setString(2, user.getEmail());
            sp.setString(3, user.getPassword());
            sp.execute();
            try (ResultSet id = sp.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Ошибка добавления пользователя {}", user.getName());
        }
        return Optional.empty();
    }

    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        LOGGER.info("Поиск пользователя по email и паролю");
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM users WHERE email = ? AND password = ?"
             )
        ) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return Optional.of(createUser(it));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Поиск пользователся не удался", e.getCause());
        }
        return Optional.empty();
    }

    private User createUser(ResultSet it) throws SQLException {
        return new User(
                it.getInt("id"),
                it.getString("name"),
                it.getString("email"),
                it.getString("password"));
    }
}
