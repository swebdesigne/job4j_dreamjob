package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ThreadSafe
@Repository
public class CandidateDBStore {
    private static final String FIND_ALL_QUERY = "SELECT * FROM Candidate";
    private static final String ADD_QUERY = "INSERT INTO Candidate (name, description, city_id, photo, created) VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM Candidate WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE Candidate SET name = ?, description = ?, city_id = ?, photo = ?, created = ? WHERE id = ?";
    private static final Logger LOGGER = LoggerFactory.getLogger(CandidateDBStore.class.getSimpleName());
    private final BasicDataSource pool;

    public CandidateDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Candidate> findAll() {
        LOGGER.info("Получение всех кандидатов");
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_ALL_QUERY)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(createCandidate(it));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Ошибка получения всех кандидатов:", e.getCause());
        }
        return candidates;
    }

    public Candidate add(Candidate candidate) {
        LOGGER.info("Добавление кандидата");
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(ADD_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setInt(3, candidate.getCity().getId());
            ps.setBytes(4, candidate.getPhoto());
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.execute();
            try (ResultSet it = ps.getGeneratedKeys()) {
                if (it.next()) {
                    candidate.setId(it.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Ошибка добавления кандидата:", e.getCause());
        }
        return candidate;
    }

    public Candidate findById(int id) {
        LOGGER.info("Получение кандидата по id");
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID_QUERY)
        ) {
            ps.setInt(1, id);
             try (ResultSet it = ps.executeQuery()) {
                 if (it.next()) {
                    return createCandidate(it);
                 }
             }
        } catch (SQLException e) {
            LOGGER.error("Ошибка получения кандидата по id:", e.getCause());
        }
        return null;
    }

    public boolean update(Candidate candidate) {
        LOGGER.info("Обновление кандидата");
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(UPDATE_QUERY)
        ) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setInt(3, candidate.getCity().getId());
            ps.setBytes(4, candidate.getPhoto());
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(6, candidate.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error("Ошибка обновления кандидата:", e.getCause());
        }
        return false;
    }

    private Candidate createCandidate(ResultSet it) throws SQLException {
        return new Candidate(
                it.getInt("id"),
                it.getString("name"),
                it.getString("description"),
                new City(it.getInt("city_id"), ""),
                it.getBytes("photo"),
                it.getTimestamp("created").toLocalDateTime()
        );
    }
}
