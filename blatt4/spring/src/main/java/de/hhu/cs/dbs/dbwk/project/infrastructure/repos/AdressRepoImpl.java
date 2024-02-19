package de.hhu.cs.dbs.dbwk.project.infrastructure.repos;

import de.hhu.cs.dbs.dbwk.project.domain.model.Adresse;
import de.hhu.cs.dbs.dbwk.project.domain.model.Nutzer;
import de.hhu.cs.dbs.dbwk.project.domain.repos.AdressRepo;
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
public class AdressRepoImpl implements AdressRepo {
    @Override
    public List<Adresse> getAdressenByStadt(String adressFilter, Connection conn) throws SQLException {
        String sql = "SELECT Wohnort.ROWID as adresseid, * FROM Wohnort WHERE Stadt LIKE ? ;";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, adressFilter);
        ResultSet resultSet = executeQuery(conn, hm, sql);
        ArrayList<Adresse> adresses = new ArrayList<>();
        while (resultSet.next()) {
            Optional<Adresse> adress = adressMapper(resultSet);
            adress.ifPresent(adresses::add);
        }
        return adresses;
    }

    @Override
    public void insertAdresse(String strasse, String hausnummer, String plz, String stadt, Connection conn) throws SQLException {
        String sql = "INSERT INTO Wohnort (Stadt, Strasse, Hausnummer, PLZ) VALUES (?,?,?,?);";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, stadt);
        hm.put(2, strasse);
        hm.put(3, hausnummer);
        hm.put(4, plz);
        executeUpdate(conn, hm, sql);
    }

    @Override
    public Optional<Adresse> getAdresseByFilter(String strasse, String hausnummer, String plz, String stadt, Connection conn) throws SQLException {
        String sql = "SELECT Wohnort.ROWID as adresseid, * FROM Wohnort WHERE Strasse = ? AND Hausnummer = ? AND PLZ = ? AND Stadt = ?";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, strasse);
        hm.put(2, hausnummer);
        hm.put(3, plz);
        hm.put(4, stadt);
        ResultSet resultSet = executeQuery(conn, hm, sql);
        return (!resultSet.next()) ? Optional.empty() : adressMapper(resultSet);
    }

    @Override
    public Optional<Adresse> getAdressByID(int adresseid, Connection conn) throws SQLException {
        String sql = "SELECT Wohnort.ROWID as adresseid, * FROM Wohnort WHERE ID = ?";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, adresseid);
        ResultSet resultSet = executeQuery(conn, hm, sql);
        return (!resultSet.next()) ? Optional.empty() : adressMapper(resultSet);
    }

    @Override
    public void deleteAdresse(Integer adresseid, Connection conn) throws SQLException {
        String sql = "DELETE FROM Wohnort WHERE ID = ?";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, adresseid);
        executeUpdate(conn, hm, sql);
    }

    @Override
    public void updateAdresse(Integer adresseid, String strasse, String hausnummer, String plz, String stadt, Connection conn) throws SQLException {
        String sql = "UPDATE Wohnort SET Stadt = ?, Strasse = ?, Hausnummer = ?, PLZ = ? WHERE ROWID = ?";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, stadt);
        hm.put(2, strasse);
        hm.put(3, hausnummer);
        hm.put(4, plz);
        hm.put(5, adresseid);
        executeUpdate(conn, hm, sql);
    }


    private Optional<Adresse> adressMapper(ResultSet resultSet) throws SQLException {
        return Optional.of(
                new Adresse(
                        resultSet.getInt("adresseid"),
                        resultSet.getString("Stadt"),
                        resultSet.getString("Strasse"),
                        resultSet.getString("Hausnummer"),
                        resultSet.getString("PLZ")));
    }
}
