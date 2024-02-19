package de.hhu.cs.dbs.dbwk.project.infrastructure.repos;

import de.hhu.cs.dbs.dbwk.project.domain.model.Standort;
import de.hhu.cs.dbs.dbwk.project.domain.repos.StandortRepo;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

import static de.hhu.cs.dbs.dbwk.project.persistence.sql.sqlite.SQLHelper.executeQuery;
import static de.hhu.cs.dbs.dbwk.project.persistence.sql.sqlite.SQLHelper.executeUpdate;

@Repository
public class StandortRepoImpl implements StandortRepo {
    @Override
    public void insertStandort(float laengenrad, float breitengrad, Connection conn) throws SQLException {
        String sql = "INSERT INTO Standort (Breitengrad, Laengengrad) VALUES (?,?);";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, breitengrad);
        hm.put(2, laengenrad);
        executeUpdate(conn, hm, sql);
    }

    @Override
    public Optional<Standort> getStandortByKoordinaten(float laengengrad, float breitengrad, Connection conn) throws SQLException {
        String sql = "SELECT ROWID as standortid, Breitengrad as breitengrad, Laengengrad as laengengrad\n" +
                "FROM Standort " +
                "WHERE round(breitengrad, 5) = round(?, 5)" +
                " AND round(laengengrad, 5) = round(?, 5);";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, breitengrad);
        hm.put(2, laengengrad);
        ResultSet resultSet = executeQuery(conn, hm, sql);
        return (!resultSet.next()) ? Optional.empty() : StandortMapper(resultSet);
    }

    private Optional<Standort> StandortMapper(ResultSet resultSet) throws SQLException {
        return Optional.of(new Standort(resultSet.getInt("standortid"),
                resultSet.getFloat("breitengrad"),
                resultSet.getFloat("laengengrad")));
    }
}
