package de.hhu.cs.dbs.dbwk.project.persistence;

import de.hhu.cs.dbs.dbwk.project.model.RoleRepository;
import de.hhu.cs.dbs.dbwk.project.model.UserRepository;

public interface PersistenceConfiguration {

    RoleRepository roleRepository();

    UserRepository userRepository();
}
