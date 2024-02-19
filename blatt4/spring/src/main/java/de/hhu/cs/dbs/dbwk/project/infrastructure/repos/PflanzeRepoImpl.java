package de.hhu.cs.dbs.dbwk.project.infrastructure.repos;

import de.hhu.cs.dbs.dbwk.project.domain.model.Nutzer;
import de.hhu.cs.dbs.dbwk.project.domain.model.Pflanze;
import de.hhu.cs.dbs.dbwk.project.domain.model.Pflanzentyp;
import de.hhu.cs.dbs.dbwk.project.domain.model.Standort;
import de.hhu.cs.dbs.dbwk.project.domain.repos.PflanzeRepo;
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
public class PflanzeRepoImpl implements PflanzeRepo {
    @Override
    public List<Pflanze> getPflanzeByEqualDname(String dname, Connection conn) throws SQLException {
        String sql = """
SELECT Pflanze.ROWID as pflanzeid,
       Pflanze.lateinische_Bezeichnung as lname,
       Pflanze.deutsche_Bezeichnung as dname,
       Pflanzentyp.Typ as pflanzentyp,
       Standort.Laengengrad as laengengrad,
       Standort.Breitengrad as breitengrad,
       Gaertner_pflanzt_Pflanze.Pflanzdatum as datum,
       Standort.ID as standortid
FROM Pflanze
         JOIN Standort ON Pflanze.Standort = Standort.ID
         LEFT JOIN Gaertner_pflanzt_Pflanze ON Pflanze.ID = Gaertner_pflanzt_Pflanze.Pflanze
         JOIN Pflanzentyp ON Pflanze.Pflanzentyp = Pflanzentyp.ID
WHERE dname = ?;
                """;
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, dname);
        ResultSet resultSet = executeQuery(conn, hm, sql);
        ArrayList<Pflanze> pflanzen = new ArrayList<>();
        while (resultSet.next()) {
            Optional<Pflanze> pflanze = pflanzeMapper(resultSet);
            pflanze.ifPresent(pflanzen::add);
        }
        return pflanzen;
    }

    @Override
    public List<Pflanze> getPflanzeByLikeDname(String dname, Connection conn) throws SQLException {
        String sql = """
SELECT Pflanze.ROWID as pflanzeid,
       Pflanze.lateinische_Bezeichnung as lname,
       Pflanze.deutsche_Bezeichnung as dname,
       Pflanzentyp.Typ as pflanzentyp,
       Standort.Laengengrad as laengengrad,
       Standort.Breitengrad as breitengrad,
       Gaertner_pflanzt_Pflanze.Pflanzdatum as datum,
       Standort.ID as standortid
FROM Pflanze
         JOIN Standort ON Pflanze.Standort = Standort.ID
         LEFT JOIN Gaertner_pflanzt_Pflanze ON Pflanze.ID = Gaertner_pflanzt_Pflanze.Pflanze
         JOIN Pflanzentyp ON Pflanze.Pflanzentyp = Pflanzentyp.ID
WHERE dname LIKE ?;
                """;
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, dname);
        ResultSet resultSet = executeQuery(conn, hm, sql);
        ArrayList<Pflanze> pflanzen = new ArrayList<>();
        while (resultSet.next()) {
            Optional<Pflanze> pflanze = pflanzeMapper(resultSet);
            pflanze.ifPresent(pflanzen::add);
        }
        return pflanzen;
    }

    @Override
    public Optional<Pflanzentyp> getPflanzentypByName(String pflanzentyp, Connection conn) throws SQLException {
        String sql = "SELECT ROWID as pflanzentypid, Typ as pflanzentyp, *" +
                " FROM Pflanzentyp" +
                " WHERE pflanzentyp = ?;";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, pflanzentyp);
        ResultSet resultSet = executeQuery(conn, hm, sql);
        return (!resultSet.next()) ? Optional.empty() : pflanzentypMapper(resultSet);
    }

    @Override
    public void insertPflanzentyp(String pflanzentyp, Connection conn) throws SQLException {
        String sql = "INSERT INTO Pflanzentyp (Typ) VALUES (?);";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, pflanzentyp);
        executeUpdate(conn, hm, sql);
    }

    @Override
    public void insertPflanze(String lname, String dname, String buergerEmail, int standortid, int pflanzentypid, Connection conn) throws SQLException {
        String sql = "INSERT INTO Pflanze (lateinische_Bezeichnung, deutsche_Bezeichnung, Email, Standort, Pflanzentyp) VALUES (?,?,?,?,?);";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, lname);
        hm.put(2, dname);
        hm.put(3, buergerEmail);
        hm.put(4, standortid);
        hm.put(5, pflanzentypid);
        executeUpdate(conn, hm, sql);
    }

    @Override
    public Optional<Pflanze> getPflanzeByID(int pflanzeid, Connection conn) throws SQLException {
        String sql = "SELECT Pflanze.ROWID as pflanzeid, Pflanze.lateinische_Bezeichnung as lname, Pflanze.deutsche_Bezeichnung as dname,\n" +
            "                   Standort.Laengengrad as laengengrad, Standort.Breitengrad as breitengrad, Pflanzentyp.Typ as pflanzentyp,\n" +
                "                Gaertner_pflanzt_Pflanze.Pflanzdatum as datum, Standort.ID as standortid\n" +
                "                FROM Pflanze\n" +
                "                JOIN Standort ON Pflanze.Standort = Standort.ID\n" +
                "                JOIN Pflanzentyp ON Pflanze.Pflanzentyp = Pflanzentyp.ID\n" +
                "                LEFT JOIN Gaertner_pflanzt_Pflanze ON Pflanze.ID = Gaertner_pflanzt_Pflanze.Pflanze\n" +
                "                WHERE Pflanze.ROWID = ?";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, pflanzeid);
        ResultSet resultSet = executeQuery(conn, hm, sql);
        return (!resultSet.next()) ? Optional.empty() : pflanzeMapper(resultSet);
    }

    private Optional<Pflanze> pflanzeMapper(ResultSet resultSet) throws SQLException {
        Standort standort = new Standort(resultSet.getInt("standortid"), resultSet.getFloat("breitengrad"), resultSet.getFloat("laengengrad"));
        return Optional.of(
                new Pflanze(
                        resultSet.getInt("pflanzeid"),
                        resultSet.getString("lname"),
                        resultSet.getString("dname"),
                        standort,
                        resultSet.getString("pflanzentyp"),
                        resultSet.getString("datum")));

    }

    private Optional<Pflanzentyp> pflanzentypMapper(ResultSet resultSet) throws SQLException {
        return Optional.of(new Pflanzentyp(resultSet.getInt("pflanzentypid"),
                resultSet.getString("pflanzentyp")));
    }
}
