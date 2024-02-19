package de.hhu.cs.dbs.dbwk.project.infrastructure.repos;

import de.hhu.cs.dbs.dbwk.project.domain.model.Gaertner;
import de.hhu.cs.dbs.dbwk.project.domain.model.Nutzer;
import de.hhu.cs.dbs.dbwk.project.domain.model.Spezialisierung;
import de.hhu.cs.dbs.dbwk.project.domain.repos.GaertnerRepo;
import de.hhu.cs.dbs.dbwk.project.domain.repos.NutzerRepo;
import de.hhu.cs.dbs.dbwk.project.domain.repos.SpezialisierungRepo;
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
public class GaertnerRepoImpl implements GaertnerRepo {

    private final NutzerRepo nutzerRepo;

    private final SpezialisierungRepo spezialisierungRepo;

    public GaertnerRepoImpl(NutzerRepo nutzerRepo, SpezialisierungRepo spezialisierungRepo) {
        this.nutzerRepo = nutzerRepo;
        this.spezialisierungRepo = spezialisierungRepo;
    }

    @Override
    public boolean isNutzerGaertner(String email, Connection conn) throws SQLException {
        //Write a query to check if the user is a buerger. This consists of checking if the user is in the buerger table.
        // If the user is in the buerger table, then the user is a buerger.
        String sql = "SELECT * FROM Gaertner WHERE Email = ?;";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, email);
        ResultSet resultSet = executeQuery(conn, hm, sql);
        return resultSet.next();
    }

    @Override
    public List<Gaertner> getGaertnersByEmail(String email, Connection conn) throws SQLException {
        String sql = "SELECT Gaertner.ROWID as gaertnerid, Spezialisierung.Name as spezialgebiet, * FROM Gaertner Join Gaertner_hat_Spezialisierung\n" +
                " ON Gaertner.Email = Gaertner_hat_Spezialisierung.Email\n" +
                " JOIN Spezialisierung ON Spezialisierung.ID = Gaertner_hat_Spezialisierung.Spezialisierung\n" +
                " WHERE Gaertner.Email LIKE ?";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, email);
        ResultSet resultSet = executeQuery(conn, hm, sql);
        ArrayList<Gaertner> gaertners = new ArrayList<>();
        while (resultSet.next()) {
            Optional<Gaertner> gaertner = gaertnerMapper(resultSet, conn);
            gaertner.ifPresent(gaertners::add);
        }
        return gaertners;
    }

    @Override
    public Optional<Gaertner> getGaertnerByEmail(String email, Connection conn) throws SQLException {
        String sql = "SELECT Gaertner.ROWID as gaertnerid, Spezialisierung.Name as spezialgebiet, * FROM Gaertner Join Gaertner_hat_Spezialisierung\n" +
                " ON Gaertner.Email = Gaertner_hat_Spezialisierung.Email\n" +
                " JOIN Spezialisierung ON Spezialisierung.ID = Gaertner_hat_Spezialisierung.Spezialisierung\n" +
                " WHERE Gaertner.Email = ?";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, email);
        ResultSet resultSet = executeQuery(conn, hm, sql);
        return (!resultSet.next()) ? Optional.empty() : gaertnerMapper(resultSet, conn);
    }


    @Override
    public void insertGaertner(String email, Connection conn) throws SQLException {
        String sql = "INSERT INTO Gaertner (Email) VALUES (?);";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, email);
        executeUpdate(conn, hm, sql);
    }

    @Override
    public void InsertGaertnerHatSpezialisierung(String email, int spezialisierungId, Connection conn) throws SQLException {
        String sql = "INSERT INTO Gaertner_hat_Spezialisierung (Email, Spezialisierung) VALUES (?,?);";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, email);
        hm.put(2, spezialisierungId);
        executeUpdate(conn, hm, sql);
    }

    @Override
    public ArrayList<Gaertner> getAllGaertner(Connection conn) throws SQLException {
        String sql = "SELECT Gaertner.ROWID as gaertnerid, Spezialisierung.Name as spezialgebiet, * FROM Gaertner Join Gaertner_hat_Spezialisierung\n" +
                " ON Gaertner.Email = Gaertner_hat_Spezialisierung.Email\n" +
                " JOIN Spezialisierung ON Spezialisierung.ID = Gaertner_hat_Spezialisierung.Spezialisierung";
        ResultSet resultSet = executeQuery(conn, new HashMap<>(), sql);
        ArrayList<Gaertner> gaertners = new ArrayList<>();
        while (resultSet.next()) {
            Optional<Gaertner> gaertner = gaertnerMapper(resultSet, conn);
            gaertner.ifPresent(gaertners::add);
        }
        return gaertners;
    }


    private Optional<Gaertner> gaertnerMapper(ResultSet resultSet, Connection conn) throws SQLException {

        Optional<Nutzer> nutzer = nutzerRepo.getNutzerByEmail(resultSet.getString("Email"), conn);
        List<Spezialisierung> spezialisierungen = spezialisierungRepo.getSpezialisierungByEmail(resultSet.getString("Email"), conn);
        return Optional.of(
                new Gaertner(
                        resultSet.getInt("gaertnerid"),
                        nutzer.orElse(new Nutzer(
                                        -1,
                                "dummy@dummy.com",
                                "dummy",
                                        "dammy",
                                        "password123")),
                        spezialisierungen
                        ));
    }
}
