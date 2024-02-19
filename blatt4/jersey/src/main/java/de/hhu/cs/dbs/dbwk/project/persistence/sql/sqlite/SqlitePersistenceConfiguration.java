package de.hhu.cs.dbs.dbwk.project.persistence.sql.sqlite;

import de.hhu.cs.dbs.dbwk.project.model.RoleRepository;
import de.hhu.cs.dbs.dbwk.project.model.UserRepository;
import de.hhu.cs.dbs.dbwk.project.persistence.sql.SqlPersistenceConfiguration;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import java.util.Properties;

import javax.sql.DataSource;

public class SqlitePersistenceConfiguration implements SqlPersistenceConfiguration {

    @Override
    public DataSource dataSource() {
        Properties properties = new Properties();
        properties.put("auto_vacuum", "full");
        SQLiteConfig config = new SQLiteConfig(properties);
        config.setEncoding(SQLiteConfig.Encoding.UTF8);
        config.enforceForeignKeys(true);
        config.setJournalMode(SQLiteConfig.JournalMode.WAL);
        config.setSynchronous(SQLiteConfig.SynchronousMode.NORMAL);
        SQLiteDataSource dataSource = new SQLiteDataSource(config);
        dataSource.setUrl("jdbc:sqlite:data/database.db");
        return dataSource;
    }

    @Override
    public RoleRepository roleRepository() {
        return null;
    }

    @Override
    public UserRepository userRepository() {
        return null;
    }
}
