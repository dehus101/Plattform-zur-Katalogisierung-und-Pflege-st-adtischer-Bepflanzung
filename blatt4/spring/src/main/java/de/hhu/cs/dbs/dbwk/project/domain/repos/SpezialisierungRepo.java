package de.hhu.cs.dbs.dbwk.project.domain.repos;

import de.hhu.cs.dbs.dbwk.project.domain.model.Spezialisierung;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface SpezialisierungRepo {
   List<Spezialisierung> getSpezialisierungByEmail(String email, Connection conn) throws SQLException;

    Optional<Spezialisierung> insertSpezialisierung(String spezialgebiet, Connection conn) throws SQLException;

    Optional<Spezialisierung> getSpezialisierungByName(String spezialgebiet, Connection conn) throws SQLException;
}
