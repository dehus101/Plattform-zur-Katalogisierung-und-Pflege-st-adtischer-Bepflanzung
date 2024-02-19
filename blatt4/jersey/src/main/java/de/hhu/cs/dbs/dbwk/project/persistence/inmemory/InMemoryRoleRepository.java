package de.hhu.cs.dbs.dbwk.project.persistence.inmemory;

import de.hhu.cs.dbs.dbwk.project.model.Role;
import de.hhu.cs.dbs.dbwk.project.model.RoleRepository;

import java.util.Set;

public class InMemoryRoleRepository implements RoleRepository {

    private final Set<Role> roles;

    public InMemoryRoleRepository(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public Set<Role> findAllRoles() {
        return Set.copyOf(roles);
    }
}
