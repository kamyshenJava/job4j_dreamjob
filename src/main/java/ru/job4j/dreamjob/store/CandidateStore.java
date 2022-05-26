package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CandidateStore {

    private static final CandidateStore INST = new CandidateStore();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private final AtomicInteger index = new AtomicInteger(1);

    private CandidateStore() {
        add(new Candidate("Alexey Alexeev",
                "junior"));
        add(new Candidate("Ivan Ivanov",
                "middle"));
        add(new Candidate("Vasiliy Vasiliev",
                "junior"));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public void add(Candidate candidate) {
        int idx = index.getAndIncrement();
        candidate.setId(idx);
        candidate.setCreated(LocalDate.now());
        candidates.put(idx, candidate);
    }

    public Candidate findById(int id) {
        return candidates.get(id);
    }

    public void update(Candidate candidate) {
        candidates.replace(candidate.getId(), candidate);
    }
}
