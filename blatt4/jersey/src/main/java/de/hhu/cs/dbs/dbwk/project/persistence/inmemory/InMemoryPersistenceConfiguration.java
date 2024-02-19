package de.hhu.cs.dbs.dbwk.project.persistence.inmemory;

import de.hhu.cs.dbs.dbwk.project.model.*;
import de.hhu.cs.dbs.dbwk.project.persistence.PersistenceConfiguration;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class InMemoryPersistenceConfiguration implements PersistenceConfiguration {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public InMemoryPersistenceConfiguration() {
        roleRepository = new InMemoryRoleRepository(roles());
        userRepository = new InMemoryUserRepository(users());
    }

    @Override
    public RoleRepository roleRepository() {
        return roleRepository;
    }

    private Set<Role> roles() {
        Role user = new SimpleRole("USER", Collections.emptySet());
        Role employee = new SimpleRole("EMPLOYEE", Set.of(user));
        Role admin = new SimpleRole("ADMIN", Set.of(employee));
        return Set.of(user, employee, admin);
    }

    @Override
    public UserRepository userRepository() {
        return userRepository;
    }

    private HashSet<User> users() {
        User foo =
                new SimpleUser(
                        "foo", "123", Set.of(new SimpleRole("USER", Collections.emptySet())));
        User bar =
                new SimpleUser(
                        "bar", "asd", Set.of(new SimpleRole("EMPLOYEE", Collections.emptySet())));
        //Return a hashset with the users foo and bar
        return new HashSet<>(Set.of(foo, bar));
    }
}
