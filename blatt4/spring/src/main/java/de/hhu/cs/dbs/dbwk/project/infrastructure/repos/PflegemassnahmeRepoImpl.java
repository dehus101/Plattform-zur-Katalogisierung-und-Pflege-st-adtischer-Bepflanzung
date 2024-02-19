package de.hhu.cs.dbs.dbwk.project.infrastructure.repos;

import de.hhu.cs.dbs.dbwk.project.domain.model.Pflanze;
import de.hhu.cs.dbs.dbwk.project.domain.model.Pflegeart;
import de.hhu.cs.dbs.dbwk.project.domain.model.Pflegemassnahme;
import de.hhu.cs.dbs.dbwk.project.domain.model.dto.PflegemassnahmeDTO;
import de.hhu.cs.dbs.dbwk.project.domain.repos.PflegemassnahmeRepo;
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
public class PflegemassnahmeRepoImpl implements PflegemassnahmeRepo {
    @Override
    public List<Pflegemassnahme> getPflegemassnahmenByPflegeartAndDurchschnittbewertung(String pflegeart, Float minAvgBewertung, Connection conn) throws SQLException {
        String sql = "SELECT Pflegemassnahme.ROWID as pflegemassnahmeid, AVG(Gaertner_bewertet_Pflegemassnahme.Skala) as avg_bewertung," +
                "Pflegemassnahme.Datum as datum, Pflegeart.Art as pflegeart\n" +
                "FROM Pflegemassnahme\n" +
                "JOIN Gaertner_bewertet_Pflegemassnahme ON Pflegemassnahme.ID = Gaertner_bewertet_Pflegemassnahme.Pflegemassnahme\n" +
                "JOIN Pflegeart ON Pflegemassnahme.Pflegeart = Pflegeart.ID\n" +
                "WHERE Pflegeart.Art LIKE ?\n" +
                "GROUP BY pflegemassnahmeid, datum, pflegeart\n" +
                "HAVING avg_bewertung >= ?";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, pflegeart);
        hm.put(2, minAvgBewertung);
        ResultSet resultSet = executeQuery(conn, hm, sql);
        ArrayList<Pflegemassnahme> pflegemassnahmen = new ArrayList<>();
        while (resultSet.next()) {
            Optional<Pflegemassnahme> pflegemassnahme = pflegemassnahmeMapper(resultSet);
            pflegemassnahme.ifPresent(pflegemassnahmen::add);
        }
        return pflegemassnahmen;
    }

    @Override
    public Optional<Pflegeart> getPflegeartByID(String pflegeart, Connection conn) throws SQLException {
        String sql = "SELECT ROWID as pflegeartid, Art as art FROM Pflegeart WHERE Art = ?;";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, pflegeart);
        ResultSet resultSet = executeQuery(conn, hm, sql);
        return (!resultSet.next()) ? Optional.empty() : pflegeartMapper(resultSet);
    }

    @Override
    public Optional<PflegemassnahmeDTO> getPflegemassnahmeByEmail(String email, Connection conn) throws SQLException {
        String sql = "SELECT Pflegemassnahme.ROWID as pflegemassnahmeid, Datum as datum, Pflegeart as pflegeartid, Email as email" +
                " FROM Pflegemassnahme WHERE Email = ?";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, email);
        ResultSet resultSet = executeQuery(conn, hm, sql);
        return (!resultSet.next()) ? Optional.empty() : pflegemassnahmeDTOMapper(resultSet);
    }

    @Override
    public void insertPflegemassnahme(String datum, int pflegeartid, String email, Connection conn) throws SQLException {
         String sql = "INSERT INTO Pflegemassnahme (Datum, Pflegeart, Email) VALUES (?,?,?);";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, datum);
        hm.put(2, pflegeartid);
        hm.put(3, email);
        executeUpdate(conn, hm, sql);
    }

    @Override
    public void insertPflegeart(String pflegeart, Connection conn) throws SQLException {
        String sql = "INSERT INTO Pflegeart (Art) VALUES (?);";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, pflegeart);
        executeUpdate(conn, hm, sql);
    }

    private Optional<Pflegeart> pflegeartMapper(ResultSet resultSet) throws SQLException {
        return Optional.of(
                new Pflegeart(
                        resultSet.getInt("pflegeartid"),
                        resultSet.getString("art")));
    }

    private Optional<PflegemassnahmeDTO> pflegemassnahmeDTOMapper(ResultSet resultSet) throws SQLException {
        return Optional.of(
                new PflegemassnahmeDTO(
                        resultSet.getInt("pflegemassnahmeid"),
                        resultSet.getString("datum"),
                        resultSet.getInt("pflegeartid"),
                        resultSet.getString("email")));
    }

    private Optional<Pflegemassnahme> pflegemassnahmeMapper(ResultSet resultSet) throws SQLException {
        return Optional.of(
                new Pflegemassnahme(
                        resultSet.getInt("pflegemassnahmeid"),
                        resultSet.getFloat("avg_bewertung"),
                        resultSet.getString("datum"),
                        resultSet.getString("pflegeart")));
    }
}
