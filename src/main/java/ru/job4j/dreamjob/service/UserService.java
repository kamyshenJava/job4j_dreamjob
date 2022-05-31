package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.store.UserDbStore;

import java.util.Optional;
@Service
public class UserService {

    private UserDbStore userDbStore;

    public UserService(UserDbStore userDbStore) {
        this.userDbStore = userDbStore;
    }

    public Optional<User> add(User user) {

        return Optional.empty();
    }
}
