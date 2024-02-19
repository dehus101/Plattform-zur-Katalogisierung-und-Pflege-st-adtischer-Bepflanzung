package de.hhu.cs.dbs.dbwk.project.infrastructure.repos;


import de.hhu.cs.dbs.dbwk.project.domain.model.Nutzer;
import de.hhu.cs.dbs.dbwk.project.domain.repos.NutzerRepo;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static de.hhu.cs.dbs.dbwk.project.persistence.sql.sqlite.SQLHelper.executeQuery;
import static de.hhu.cs.dbs.dbwk.project.persistence.sql.sqlite.SQLHelper.executeUpdate;


@Repository
public class NutzerRepoImpl implements NutzerRepo {

    public ArrayList<Nutzer> getNutzerByEmailAndNachname(
            String email, String nachname, Connection connection) throws SQLException {
        String sql = "SELECT ROWID as nutzerid, * FROM Nutzer WHERE Email LIKE ? AND Nachname LIKE ?;";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, email);
        hm.put(2, nachname);
        ResultSet resultSet = executeQuery(connection, hm, sql);
        ArrayList<Nutzer> nutzers = new ArrayList<>();
        while (resultSet.next()) {
            Optional<Nutzer> nutzer = nutzerMapper(resultSet);
            nutzer.ifPresent(nutzers::add);
        }
        return nutzers;
    }

    @Override
    public Optional<Nutzer> getNutzerByEmail(String email, Connection conn) throws SQLException {
        String sql = "SELECT ROWID as nutzerid, * FROM Nutzer WHERE Email = ?;";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, email);
        ResultSet resultSet = executeQuery(conn, hm, sql);
        return (!resultSet.next()) ? Optional.empty() : nutzerMapper(resultSet);
    }

    @Override
    public Optional<Nutzer> insertNutzer(String email, String passwort, String vorname, String nachname, Connection conn) throws SQLException {
        String sql = "INSERT INTO Nutzer (Email, Vorname, Nachname, Passwort) VALUES (?,?,?,?);";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, email);
        hm.put(2, vorname);
        hm.put(3, nachname);
        hm.put(4, passwort);
        executeUpdate(conn, hm, sql);
        return getNutzerByEmail(email, conn);
    }

    @Override
    public ArrayList<Nutzer> getAllNutzer(Connection conn) throws SQLException {
        String sql = "SELECT ROWID as nutzerid, * FROM Nutzer;";
        ResultSet resultSet = executeQuery(conn, new HashMap<>() ,sql);
        ArrayList<Nutzer> nutzers = new ArrayList<>();
        while (resultSet.next()) {
            Optional<Nutzer> nutzer = nutzerMapper(resultSet);
            nutzer.ifPresent(nutzers::add);
        }
        return nutzers;
    }

    private Optional<Nutzer> nutzerMapper(ResultSet resultSet) throws SQLException {
        return Optional.of(
                new Nutzer(
                        resultSet.getInt("nutzerid"),
                        resultSet.getString("Email"),
                        resultSet.getString("Vorname"),
                        resultSet.getString("Nachname"),
                        resultSet.getString("Passwort")));
    }
}
