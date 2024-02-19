package de.hhu.cs.dbs.dbwk.project.domain.repos;


import de.hhu.cs.dbs.dbwk.project.domain.model.Buerger;
import de.hhu.cs.dbs.dbwk.project.domain.model.Nutzer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public interface NutzerRepo {

    ArrayList<Nutzer> getNutzerByEmailAndNachname(String email, String nachname, Connection connection)
            throws SQLException;

    Optional<Nutzer> getNutzerByEmail(String email, Connection conn) throws SQLException;

    Optional<Nutzer> insertNutzer(String email, String passwort, String vorname, String nachname, Connection conn) throws SQLException;

    ArrayList<Nutzer> getAllNutzer(Connection conn) throws SQLException;
}
