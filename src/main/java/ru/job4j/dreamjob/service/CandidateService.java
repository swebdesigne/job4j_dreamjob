package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateDBStore;

import java.util.Collection;
import java.util.List;

@ThreadSafe
@Service
public class CandidateService {
    private final CandidateDBStore candidateStore;
    private final CityService cityService;

    public CandidateService(CandidateDBStore store, CityService cityService) {
        this.candidateStore = store;
        this.cityService = cityService;
    }

    public Collection<Candidate> findAll() {
        List<Candidate> candidates = candidateStore.findAll();
        candidates.forEach(
                candidate -> candidate.setCity(
                        cityService.findById(candidate.getCity().getId())
                )
        );
        return candidates;
    }

    public void update(Candidate candidate) {
        candidate.setCity(cityService.findById(candidate.getCity().getId()));
        candidateStore.update(candidate);
    }

    public void add(Candidate candidate) {
        candidate.setCity(cityService.findById(candidate.getCity().getId()));
        candidateStore.add(candidate);
    }

    public Candidate findById(int id) {
        return candidateStore.findById(id);
    }
}