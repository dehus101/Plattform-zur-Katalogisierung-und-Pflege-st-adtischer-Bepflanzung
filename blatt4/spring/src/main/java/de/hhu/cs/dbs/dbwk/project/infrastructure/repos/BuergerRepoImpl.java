package de.hhu.cs.dbs.dbwk.project.infrastructure.repos;

import de.hhu.cs.dbs.dbwk.project.domain.model.Adresse;
import de.hhu.cs.dbs.dbwk.project.domain.model.Buerger;
import de.hhu.cs.dbs.dbwk.project.domain.model.Nutzer;
import de.hhu.cs.dbs.dbwk.project.domain.repos.AdressRepo;
import de.hhu.cs.dbs.dbwk.project.domain.repos.BuergerRepo;
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
public class BuergerRepoImpl implements BuergerRepo {

    private final NutzerRepo nutzerRepo;

    private final AdressRepo adressRepo;

    public BuergerRepoImpl(NutzerRepo nutzerRepo, AdressRepo adressRepo) {
        this.nutzerRepo = nutzerRepo;
        this.adressRepo = adressRepo;
    }
    @Override
    public boolean isNutzerBuerger(String email, Connection conn) throws SQLException {
        //Write a query to check if the user is a buerger. This consists of checking if the user is in the buerger table.
        // If the user is in the buerger table, then the user is a buerger.
        String sql = "SELECT * FROM Buerger WHERE Email = ?;";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, email);
        ResultSet resultSet = executeQuery(conn, hm, sql);
        return resultSet.next();
    }

    @Override
    public List<Buerger> getBuergerbyStadt(String stadt, Connection conn) throws SQLException {
        String sql = "SELECT Buerger.ROWID as buergerid, Wohnort.ID as adresseid, * FROM Buerger," +
                " Wohnort WHERE Buerger.Wohnort = Wohnort.ID And Wohnort.Stadt LIKE ?;";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, stadt);
        ResultSet resultSet = executeQuery(conn, hm, sql);
        ArrayList<Buerger> nutzers = new ArrayList<>();
        while (resultSet.next()) {
            Optional<Buerger> buerger = buergerMapper(resultSet, conn);
            buerger.ifPresent(nutzers::add);
        }
        return nutzers;
    }

    @Override
    public Optional<Buerger> getBuergerByEmail(String email, Connection conn) throws SQLException {
        String sql = "SELECT Buerger.ROWID as buergerid, Wohnort.ID as adresseid,  * FROM Buerger, Wohnort\n" +
                "WHERE Buerger.Wohnort = Wohnort.ID AND Email = ?";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, email);
        ResultSet resultSet = executeQuery(conn, hm, sql);
        return (!resultSet.next()) ? Optional.empty() : buergerMapper(resultSet, conn);
    }

    @Override
    public Optional<Buerger> insertBuerger(String email, int adresseid, Connection conn) throws SQLException {
        String sql = "INSERT INTO Buerger (Email, Wohnort) VALUES (?,?);";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, email);
        hm.put(2, adresseid);
        executeUpdate(conn, hm, sql);
        return getBuergerByEmail(email, conn);
    }

    @Override
    public Optional<Buerger> getBuergerByAdresseID(Integer adresseid, Connection conn) throws SQLException {
        String sql = "SELECT Buerger.ROWID as buergerid, Wohnort.ROWID as adresseid, *" +
                " FROM Wohnort" +
                " JOIN Buerger ON Wohnort.ID = Buerger.Wohnort " +
                " WHERE Wohnort.ROWID = ?";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, adresseid);
        ResultSet resultSet = executeQuery(conn, hm, sql);
        return (!resultSet.next()) ? Optional.empty() : buergerMapper(resultSet, conn);
    }

    @Override
    public ArrayList<Buerger> getAllBuerger(Connection conn) throws SQLException {
        String sql = "SELECT Buerger.ROWID as buergerid, Wohnort.ROWID as adresseid, * FROM Buerger, Wohnort WHERE Buerger.Wohnort = Wohnort.ID;";
        ResultSet resultSet = executeQuery(conn, new HashMap<>(), sql);
        ArrayList<Buerger> buergers = new ArrayList<>();
        while (resultSet.next()) {
            Optional<Buerger> buerger = buergerMapper(resultSet, conn);
            buerger.ifPresent(buergers::add);
        }
        return buergers;
    }


    private Optional<Buerger> buergerMapper(ResultSet resultSet, Connection conn) throws SQLException {

        Optional<Nutzer> nutzer = nutzerRepo.getNutzerByEmail(
                resultSet.getString("Email"),
                conn);
        /*Optional<Adresse> adresse = adressRepo.getAdresseByFilter(
                resultSet.getString("Strasse"),
                resultSet.getString("Hausnummer"),
                resultSet.getString("PLZ"),
                resultSet.getString("Stadt"),
                conn);*/

        Optional<Adresse> adresse = adressRepo.getAdressByID(resultSet.getInt("adresseid"), conn);
        return Optional.of(
                new Buerger(
                        resultSet.getInt("buergerid"),
                        nutzer.orElse(new Nutzer(
                                        -1,
                                "dummy@dummy.com",
                                "dummy",
                                        "dammy",
                                        "password123")),
                        adresse.orElse(new Adresse(
                                -1,
                                "dummyStadt",
                                "dummyStrasse",
                                "dummyHausnummer",
                                "dummyPLZ"))
                )
        );
    }
}
