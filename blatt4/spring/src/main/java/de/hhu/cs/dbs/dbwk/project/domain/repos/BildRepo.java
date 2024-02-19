package de.hhu.cs.dbs.dbwk.project.domain.repos;

import de.hhu.cs.dbs.dbwk.project.domain.model.Bild;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BildRepo {
    List<Bild> getBilderByPflanzeID(int pflanzeid, Connection conn) throws SQLException;

    void insertBild(int pflanzeid, String pfad, Connection conn) throws SQLException;

    Optional<Bild> getBildByBildID(int bildid, Connection conn) throws SQLException;

    void deleteBild(int bildId, int pflanzeId, Connection conn) throws SQLException;

    void updateBild(int pflanzeid, int bildid, String pfad, Connection conn) throws SQLException;
}
