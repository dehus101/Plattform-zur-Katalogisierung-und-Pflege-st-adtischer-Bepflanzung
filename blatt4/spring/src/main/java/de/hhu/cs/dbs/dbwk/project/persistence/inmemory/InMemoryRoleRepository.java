package de.hhu.cs.dbs.dbwk.project.persistence.inmemory;

import de.hhu.cs.dbs.dbwk.project.domain.model.Role;
import de.hhu.cs.dbs.dbwk.project.domain.model.RoleRepository;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@ConditionalOnMissingBean(name = "roleRepository")
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
