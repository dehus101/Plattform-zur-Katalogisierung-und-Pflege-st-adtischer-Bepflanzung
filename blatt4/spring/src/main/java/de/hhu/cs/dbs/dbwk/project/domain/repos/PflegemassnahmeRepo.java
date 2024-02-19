package de.hhu.cs.dbs.dbwk.project.domain.repos;

import de.hhu.cs.dbs.dbwk.project.domain.model.Pflegeart;
import de.hhu.cs.dbs.dbwk.project.domain.model.Pflegemassnahme;
import de.hhu.cs.dbs.dbwk.project.domain.model.dto.PflegemassnahmeDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PflegemassnahmeRepo {
    List<Pflegemassnahme> getPflegemassnahmenByPflegeartAndDurchschnittbewertung(String pflegeart, Float minAvgBewertung, Connection conn) throws SQLException;

    Optional<Pflegeart> getPflegeartByID(String pflegeart, Connection conn) throws SQLException;

    Optional<PflegemassnahmeDTO> getPflegemassnahmeByEmail(String email, Connection conn) throws SQLException;

    void insertPflegemassnahme(String datum, int pflegeartid, String email, Connection conn) throws SQLException;

    void insertPflegeart(String pflegeart, Connection conn) throws SQLException;
}
