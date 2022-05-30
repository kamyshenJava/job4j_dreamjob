package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CandidateDbStore {
    private final BasicDataSource pool;

    public CandidateDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidate ORDER BY id")) {
            try (ResultSet rsl = ps.executeQuery()) {
                while (rsl.next()) {
                    candidates.add(new Candidate(rsl.getInt("id"), rsl.getString("name"),
                            rsl.getString("description"), rsl.getDate("created").toLocalDate(),
                            rsl.getBytes("photo")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidates;
    }

    public Candidate add(Candidate candidate) {
        LocalDate created = LocalDate.now();
        candidate.setCreated(created);
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO candidate(name, description, created, photo) VALUES (?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setDate(3, Date.valueOf(candidate.getCreated()));
            ps.setBytes(4, candidate.getPhoto());
            ps.execute();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    candidate.setId(rs.getInt("id"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidate;
    }

    public Candidate findById(int id) {
        Candidate rsl = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidate WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rsl = new Candidate(id, rs.getString("name"), rs.getString("description"),
                            rs.getDate("created").toLocalDate(), rs.getBytes("photo"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    public void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
        PreparedStatement ps = cn.prepareStatement(
                "UPDATE candidate SET name = ?, description = ?, created = ?, photo = ? WHERE id = ?")) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setDate(3, Date.valueOf(candidate.getCreated()));
            ps.setBytes(4, candidate.getPhoto());
            ps.setInt(5, candidate.getId());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
