package de.hhu.cs.dbs.dbwk.project.domain.repos;

import de.hhu.cs.dbs.dbwk.project.domain.model.Pflegeprotokoll;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface PflegeprotokollRepo {
    List<Pflegeprotokoll> getPflegeprotokolle(Connection conn) throws SQLException;
}
