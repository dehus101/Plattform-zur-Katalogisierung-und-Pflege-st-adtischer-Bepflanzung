package de.hhu.cs.dbs.dbwk.project.persistence.inmemory;

import de.hhu.cs.dbs.dbwk.project.domain.model.User;
import de.hhu.cs.dbs.dbwk.project.domain.model.UserRepository;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

//@Repository
//@ConditionalOnMissingBean(name = "userRepository")
public class InMemoryUserRepository implements UserRepository {

    private final HashSet<User> users;

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
