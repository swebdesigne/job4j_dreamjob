package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class CandidateStore {
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final AtomicInteger inc = new AtomicInteger(3);

    public CandidateStore() {
        candidates.put(1, new Candidate(1, "Junior Java Job", "Candidate 1", new City(), LocalDate.now()));
        candidates.put(2, new Candidate(2, "Middle Java Job", "Candidate 2", new City(), LocalDate.now()));
        candidates.put(3, new Candidate(3, "Senior Java Job", "Candidate 3", new City(), LocalDate.now()));
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public void add(Candidate candidate) {
        candidate.setId(inc.incrementAndGet());
        candidates.put(candidate.getId(), candidate);
    }

    public Object findById(int id) {
        return candidates.get(id);
    }

    public void update(Candidate candidate) {
        candidates.replace(candidate.getId(), candidate);
    }
}
