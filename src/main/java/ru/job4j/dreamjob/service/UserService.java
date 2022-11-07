package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.repository.UserDBStore;

import java.util.Optional;

@ThreadSafe
@Service
public class UserService {
    private final UserDBStore userStore;

    public UserService(UserDBStore useStore) {
        this.userStore = useStore;
    }

    public Optional<User> add(User user) {
        return userStore.add(user);
    }
}
