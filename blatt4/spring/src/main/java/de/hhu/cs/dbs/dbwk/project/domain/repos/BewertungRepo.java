package de.hhu.cs.dbs.dbwk.project.domain.repos;

import de.hhu.cs.dbs.dbwk.project.domain.model.Bewertung;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BewertungRepo {
    List<Bewertung> getBewertungen(Connection conn) throws SQLException;
}
