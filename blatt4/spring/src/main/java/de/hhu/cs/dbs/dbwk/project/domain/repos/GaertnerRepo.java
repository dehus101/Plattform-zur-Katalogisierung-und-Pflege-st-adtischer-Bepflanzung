package de.hhu.cs.dbs.dbwk.project.domain.repos;

import de.hhu.cs.dbs.dbwk.project.domain.model.Gaertner;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface GaertnerRepo {
    boolean isNutzerGaertner(String email, Connection conn) throws SQLException;

    List<Gaertner> getGaertnersByEmail(String email, Connection conn) throws SQLException;

    Optional<Gaertner> getGaertnerByEmail(String email, Connection conn) throws SQLException;

    void insertGaertner(String email, Connection conn) throws SQLException;

    void InsertGaertnerHatSpezialisierung(String email, int spezialisierungId, Connection conn) throws SQLException;

    ArrayList<Gaertner> getAllGaertner(Connection conn) throws SQLException;
}
