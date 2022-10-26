package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CandidateStore {
    private static final CandidateStore INST = new CandidateStore();
    private final Map<Integer, Candidate> candidate = new ConcurrentHashMap<>();

    public CandidateStore() {
        candidate.put(1, new Candidate(1, "Junior Java Job", "Candidate 1", LocalDate.now()));
        candidate.put(2, new Candidate(2, "Middle Java Job", "Candidate 2", LocalDate.now()));
        candidate.put(3, new Candidate(3, "Senior Java Job", "Candidate 3", LocalDate.now()));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidate.values();
    }
}
