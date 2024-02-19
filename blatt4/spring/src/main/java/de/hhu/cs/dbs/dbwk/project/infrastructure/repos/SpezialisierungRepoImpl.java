package de.hhu.cs.dbs.dbwk.project.infrastructure.repos;

import de.hhu.cs.dbs.dbwk.project.domain.model.Buerger;
import de.hhu.cs.dbs.dbwk.project.domain.model.Nutzer;
import de.hhu.cs.dbs.dbwk.project.domain.model.Spezialisierung;
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
public class SpezialisierungRepoImpl implements SpezialisierungRepo {

    public List<Spezialisierung> getSpezialisierungByEmail(String email, Connection conn) throws SQLException {
        String sql = "SELECT Spezialisierung.ROWID as spezialisierungid, Spezialisierung.Name as spezialgebiet FROM Spezialisierung" +
                " Join Gaertner_hat_Spezialisierung\n" +
                " ON Spezialisierung.ID = Gaertner_hat_Spezialisierung.Spezialisierung\n" +
                "WHERE Email LIKE ?";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, email);
        ResultSet resultSet = executeQuery(conn, hm, sql);
        ArrayList<Spezialisierung> spezialisierungen = new ArrayList<>();
        while (resultSet.next()) {
            Optional<Spezialisierung> spezialisierung = spezialisierungMapper(resultSet);
            spezialisierung.ifPresent(spezialisierungen::add);
        }
        return spezialisierungen;
    }

    @Override
    public Optional<Spezialisierung> getSpezialisierungByName(String spezialgebiet, Connection conn) throws SQLException {
        String sql = "SELECT ROWID as spezialisierungid,Name as spezialgebiet, * FROM Spezialisierung WHERE spezialgebiet = ?;";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, spezialgebiet);
        ResultSet resultSet = executeQuery(conn, hm, sql);
        return (!resultSet.next()) ? Optional.empty() : spezialisierungMapper(resultSet);
    }

    @Override
    public Optional<Spezialisierung> insertSpezialisierung(String spezialgebiet,  Connection conn) throws SQLException {
        String sql = "INSERT INTO Spezialisierung (Name) VALUES (?);";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, spezialgebiet);
        executeUpdate(conn, hm, sql);
        return getSpezialisierungByName(spezialgebiet, conn);
    }

    private Optional<Spezialisierung> spezialisierungMapper(ResultSet resultSet) throws SQLException {
        return Optional.of(
                new Spezialisierung(
                        resultSet.getInt("spezialisierungid"),
                        resultSet.getString("spezialgebiet")));
    }

}
