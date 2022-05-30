package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostDbStore {

    private final BasicDataSource pool;

    public PostDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post ORDER BY id")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"), it.getString("name"),
                            it.getString("description"), it.getDate("created").toLocalDate(),
                            new City(it.getInt("city_id")), it.getBoolean("visible")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }


    public Post add(Post post) {
        LocalDate created = LocalDate.now();
        post.setCreated(created);
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "INSERT INTO post(name, description, created, city_id, visible) VALUES (?, ?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setDate(3, Date.valueOf(created));
            ps.setInt(4, post.getCity().getId());
            ps.setBoolean(5, post.isVisible());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    public void update(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "UPDATE post SET name = ?, DESCRIPTION = ?, CITY_ID = ?, VISIBLE = ? WHERE id = ?")) {

            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setInt(3, post.getCity().getId());
            ps.setBoolean(4, post.isVisible());
            ps.setInt(5, post.getId());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Post findById(int id) {
        Post rsl = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    rsl = new Post(id, it.getString("name"),
                            it.getString("description"), it.getDate("created").toLocalDate(),
                            new City(it.getInt("city_id")), it.getBoolean("visible"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }
}
