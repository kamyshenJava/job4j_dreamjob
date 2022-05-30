package ru.job4j.dreamjob.store;

import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CandidateDbStoreTest {
    @Test
    public void whenCreateCandidate() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        Candidate candidate = new Candidate("Java Job", "description");
        store.add(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName(), is(candidate.getName()));
    }

    @Test
    public void whenCreateAndUpdateCandidate() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        Candidate candidate = new Candidate("Java Job", "description");
        store.add(candidate);
        store.update(new Candidate("Java", "description2"));
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName(), is("Java"));
        assertThat(candidateInDb.getDescription(), is("description2"));
    }

    @Test
    public void whenCreateCandidatesAndFindAll() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        Candidate candidate = new Candidate("Java Job", "description");
        Candidate candidate2 = new Candidate("Java", "description2");
        Candidate candidate3 = new Candidate("Java3", "description3");
        store.add(candidate);
        store.add(candidate2);
        store.add(candidate3);
        assertThat(store.findAll().size(), is(3));
    }
}