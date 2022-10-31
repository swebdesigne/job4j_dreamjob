package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateStore;

import java.util.Collection;

@ThreadSafe
@Service
public class CandidateService {
    private final CandidateStore candidateStore;
    private final CityService cityService;

    public CandidateService(CandidateStore store, CityService cityService) {
        this.candidateStore = store;
        this.cityService = cityService;
    }

    public Collection<Candidate> findAll() {
        return candidateStore.findAll();
    }

    public void update(Candidate candidate) {
        candidate.setCity(cityService.findById(candidate.getCity().getId()));
        candidateStore.update(candidate);
    }

    public void add(Candidate candidate) {
        candidate.setCity(cityService.findById(candidate.getCity().getId()));
        candidateStore.add(candidate);
    }

    public Object findById(int id) {
        return candidateStore.findById(id);
    }
}