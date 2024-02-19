package de.hhu.cs.dbs.dbwk.project.domain.repos;

import de.hhu.cs.dbs.dbwk.project.domain.model.Buerger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface BuergerRepo {
    boolean isNutzerBuerger(String email, Connection conn) throws SQLException;

    List<Buerger> getBuergerbyStadt(String stadt, Connection conn) throws SQLException;

    Optional<Buerger> getBuergerByEmail(String email, Connection conn) throws SQLException;

    Optional<Buerger> insertBuerger(String email, int adresseid, Connection conn) throws SQLException;

    Optional<Buerger> getBuergerByAdresseID(Integer adresseid, Connection conn) throws SQLException;

    ArrayList<Buerger> getAllBuerger(Connection conn) throws SQLException;
}
