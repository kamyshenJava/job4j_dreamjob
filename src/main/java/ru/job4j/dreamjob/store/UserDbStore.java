package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.User;

@Repository
public class UserDbStore {

    private static final Logger LOG = LoggerFactory.getLogger(UserDbStore.class.getName());
    private final BasicDataSource pool;

    public UserDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public User add(User user) {

        return user;
    }
}
