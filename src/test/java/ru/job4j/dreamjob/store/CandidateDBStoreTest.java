package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.AfterClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CandidateDBStoreTest {
    private final static BasicDataSource POOL = new Main().loadPool();
    private final CandidateDBStore store = new CandidateDBStore(POOL);

    @AfterEach
    public void wipeTable() throws SQLException {
        try (Connection cn = POOL.getConnection();
             PreparedStatement ps = cn.prepareStatement("TRUNCATE TABLE candidate")
        ) {
            ps.execute();
        }
    }

    @AfterClass
    public static void closeCnPool() throws SQLException {
        POOL.close();
    }

    @Test
    public void whenCreateCandidate() {
        Candidate candidate = new Candidate(
                0, "Igor", "Java Developer", new City(0, ""), new byte[0], LocalDateTime.now()
        );
        store.add(candidate);
        Candidate candidateFInDb = store.findById(candidate.getId());
        assertThat(candidateFInDb.getName()).isEqualTo(candidate.getName());
    }

    @Test
    public void whenFindAllCandidates() {
        Candidate candidate = new Candidate(
                0, "Igor", "Java Developer", new City(0, ""), new byte[0], LocalDateTime.now()
        );
        List<Candidate> listCandidate = List.of(
                candidate,
                new Candidate(
                        1, "Boris", "C++ Developer", new City(1, ""), new byte[2], LocalDateTime.now()
                )
        );
        listCandidate.forEach(store::add);
        List<Candidate> getCandidates = store.findAll();
        assertThat(getCandidates).isEqualTo(listCandidate);
    }

    @Test
    public void whenUpdateCandidate() {
        Candidate candidate = new Candidate(
                0, "Igor", "Java Developer", new City(0, ""), new byte[0], LocalDateTime.now()
        );
        store.add(candidate);
        Candidate newCandidate = new Candidate(
                candidate.getId(), "Igor", "Python Developer", new City(0, ""), new byte[3], LocalDateTime.now()
        );
        store.update(newCandidate);
        Candidate findIdPost = store.findById(candidate.getId());
        assertThat(findIdPost.getDescription()).isEqualTo(newCandidate.getDescription());
    }
}