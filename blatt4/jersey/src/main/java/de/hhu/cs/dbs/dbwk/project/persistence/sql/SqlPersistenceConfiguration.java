package de.hhu.cs.dbs.dbwk.project.persistence.sql;

import de.hhu.cs.dbs.dbwk.project.persistence.PersistenceConfiguration;

import javax.sql.DataSource;

public interface SqlPersistenceConfiguration extends PersistenceConfiguration {

    DataSource dataSource();
}
