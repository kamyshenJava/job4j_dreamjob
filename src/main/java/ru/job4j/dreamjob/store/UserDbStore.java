package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository
public class UserDbStore {

    private static final Logger LOG = LoggerFactory.getLogger(UserDbStore.class.getName());
    private final BasicDataSource pool;

    public UserDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public User add(User user) {
        User rsl = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO users(email, password) VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.execute();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getInt("id"));
                    rsl = user;
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in UserDbStore", e);
        }
        return rsl;
    }

    public User findUserByEmailAndPwd(String email, String password) {
        User rsl = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?")) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rsl = new User(rs.getInt("id"), rs.getString("email"),
                            rs.getString("password"));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in UserDbStore", e);
        }
        return rsl;
    }
}
