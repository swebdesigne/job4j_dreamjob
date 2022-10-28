package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateStore;

import java.util.Collection;

@Service
public class CandidateService {
    private final static CandidateService INST = new CandidateService();
    private final CandidateStore store = CandidateStore.instOf();

    public static CandidateService instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return store.findAll();
    }

    public void update(Candidate candidate) {
        store.update(candidate);
    }

    public void add(Candidate candidate) {
        store.add(candidate);
    }

    public Object findById(int id) {
        return store.findById(id);
    }
}