package de.hhu.cs.dbs.dbwk.project.domain.repos;

import de.hhu.cs.dbs.dbwk.project.domain.model.Pflanze;
import de.hhu.cs.dbs.dbwk.project.domain.model.Pflanzentyp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PflanzeRepo {
    List<Pflanze> getPflanzeByEqualDname(String dname, Connection conn) throws SQLException;

    List<Pflanze> getPflanzeByLikeDname(String dname, Connection conn) throws SQLException;

    Optional<Pflanzentyp> getPflanzentypByName(String pflanzentyp, Connection conn) throws SQLException;

    void insertPflanzentyp(String pflanzentyp, Connection conn) throws SQLException;

    void insertPflanze(String lname, String dname, String buergerEmail, int standortid, int pflanzentypid, Connection conn) throws SQLException;

    Optional<Pflanze> getPflanzeByID(int pflanzeid, Connection conn) throws SQLException;
}
