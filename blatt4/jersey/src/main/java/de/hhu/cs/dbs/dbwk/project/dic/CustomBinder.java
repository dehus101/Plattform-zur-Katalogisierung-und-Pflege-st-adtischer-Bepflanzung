package de.hhu.cs.dbs.dbwk.project.dic;

import de.hhu.cs.dbs.dbwk.project.model.RoleRepository;
import de.hhu.cs.dbs.dbwk.project.model.UserRepository;
import de.hhu.cs.dbs.dbwk.project.persistence.PersistenceConfiguration;
import de.hhu.cs.dbs.dbwk.project.persistence.sql.sqlite.SqlitePersistenceConfiguration;
import de.hhu.cs.dbs.dbwk.project.security.AuthenticationService;
import de.hhu.cs.dbs.dbwk.project.security.SecurityConfiguration;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.sql.DataSource;

public class CustomBinder extends AbstractBinder {

    private final PersistenceConfiguration persistenceConfiguration;
    private final SecurityConfiguration securityConfiguration;

    public CustomBinder(
            PersistenceConfiguration persistenceConfiguration,
            SecurityConfiguration securityConfiguration) {
        this.persistenceConfiguration = persistenceConfiguration;
        this.securityConfiguration = securityConfiguration;
    }

    @Override
    protected void configure() {
        if (persistenceConfiguration instanceof SqlitePersistenceConfiguration) {
            bind(((SqlitePersistenceConfiguration) persistenceConfiguration).dataSource())
                    .to(DataSource.class);
        }
        bind(persistenceConfiguration.roleRepository()).to(RoleRepository.class);
        bind(persistenceConfiguration.userRepository()).to(UserRepository.class);
        bind(securityConfiguration.authenticationService()).to(AuthenticationService.class);
    }
}
