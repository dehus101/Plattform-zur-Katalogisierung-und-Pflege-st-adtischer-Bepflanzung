package de.hhu.cs.dbs.dbwk.project.api.persistence.inmemory;

import de.hhu.cs.dbs.dbwk.project.api.model.Role;
import de.hhu.cs.dbs.dbwk.project.api.model.User;
import de.hhu.cs.dbs.dbwk.project.api.model.SimpleRole;
import de.hhu.cs.dbs.dbwk.project.api.model.SimpleUser;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Set;

@Configuration
public class InMemoryConfiguration {

    @ConditionalOnBean(InMemoryRoleRepository.class)
    @Bean
    Set<Role> roles() {
        Role user = new SimpleRole("USER", Collections.emptySet());
        Role employee = new SimpleRole("EMPLOYEE", Set.of(user));
        Role admin = new SimpleRole("ADMIN", Set.of(employee));
        return Set.of(user, employee, admin);
    }

    @ConditionalOnBean(InMemoryUserRepository.class)
    @Bean
    Set<User> users() {
        User foo =
                new SimpleUser(
                        "foo", "{noop}123", Set.of(new SimpleRole("USER", Collections.emptySet())));
        User bar =
                new SimpleUser(
                        "bar",
                        "{noop}asd",
                        Set.of(new SimpleRole("EMPLOYEE", Collections.emptySet())));
        return Set.of(foo, bar);
    }
}
