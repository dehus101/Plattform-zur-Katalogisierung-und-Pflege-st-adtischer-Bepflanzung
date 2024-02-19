package de.hhu.cs.dbs.dbwk.project.infrastructure.repos;

import de.hhu.cs.dbs.dbwk.project.domain.model.Bewertung;
import de.hhu.cs.dbs.dbwk.project.domain.model.Bild;
import de.hhu.cs.dbs.dbwk.project.domain.repos.BildRepo;
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
public class BildRepoImpl implements BildRepo {
    @Override
    public List<Bild> getBilderByPflanzeID(int pflanzeid, Connection conn) throws SQLException {
        String sql = "SELECT Bild.ROWID as bildid, Pflanze.ROWID as pflanzeid, Bild.Bildpfad as pfad" +
                " FROM Bild" +
                " JOIN Pflanze ON BILD.Pflanze = Pflanze.ID" +
                " WHERE Pflanze.ROWID = ?";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, pflanzeid);
        ResultSet resultSet = executeQuery(conn, hm, sql);
        ArrayList<Bild> bilder = new ArrayList<>();
        while (resultSet.next()) {
            Optional<Bild> bild = bildMapper(resultSet);
            bild.ifPresent(bilder::add);
        }
        return bilder;
    }

    @Override
    public void insertBild(int pflanzeid, String pfad, Connection conn) throws SQLException {
        String sql = "INSERT INTO Bild (Bildpfad, Pflanze) VALUES (?,?);";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, pfad);
        hm.put(2, pflanzeid);
        executeUpdate(conn, hm, sql);
    }

    @Override
    public Optional<Bild> getBildByBildID(int bildid, Connection conn) throws SQLException {
        String sql = "SELECT Bild.ROWID as bildid, Pflanze.ROWID as pflanzeid, Bild.Bildpfad as pfad" +
                " FROM Bild" +
                " JOIN Pflanze ON BILD.Pflanze = Pflanze.ID" +
                " WHERE Bild.ROWID = ?";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, bildid);
        ResultSet resultSet = executeQuery(conn, hm, sql);
        return (!resultSet.next()) ? Optional.empty() : bildMapper(resultSet);
    }

    @Override
    public void deleteBild(int bildId, int pflanzeId, Connection conn) throws SQLException {
        String sql = "DELETE FROM Bild WHERE ROWID = ? AND Pflanze = ? ";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, bildId);
        hm.put(2, pflanzeId);
        executeUpdate(conn, hm, sql);
    }

    @Override
    public void updateBild(int pflanzeid, int bildid, String pfad, Connection conn) throws SQLException {
        String sql = "UPDATE Bild SET Bildpfad = ? WHERE ROWID = ? AND Pflanze = ?";
        HashMap<Integer, Object> hm = new HashMap<>();
        hm.put(1, pfad);
        hm.put(2, bildid);
        hm.put(3, pflanzeid);
        executeUpdate(conn, hm, sql);

    }

    private Optional<Bild> bildMapper(ResultSet resultSet) throws SQLException {
        return Optional.of(new Bild(resultSet.getInt("bildid")
                ,resultSet.getInt("pflanzeid"),
                resultSet.getString("pfad")));
    }
}
