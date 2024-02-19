package de.hhu.cs.dbs.dbwk.project.persistence.inmemory;

import de.hhu.cs.dbs.dbwk.project.model.User;
import de.hhu.cs.dbs.dbwk.project.model.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class InMemoryUserRepository implements UserRepository {

    private final Set<User> users;

    public InMemoryUserRepository(HashSet<User> users) {
        this.users = users;
    }

    @Override
    public Optional<User> findUser(String uniqueString) {
        return users.stream()
                .filter(user -> uniqueString.equals(user.getUniqueString()))
                .findFirst();
    }
}
