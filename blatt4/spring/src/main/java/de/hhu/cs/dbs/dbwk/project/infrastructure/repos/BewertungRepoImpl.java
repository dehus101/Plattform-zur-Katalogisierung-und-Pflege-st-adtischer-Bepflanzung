package de.hhu.cs.dbs.dbwk.project.infrastructure.repos;

import de.hhu.cs.dbs.dbwk.project.domain.model.Bewertung;
import de.hhu.cs.dbs.dbwk.project.domain.model.Buerger;
import de.hhu.cs.dbs.dbwk.project.domain.repos.BewertungRepo;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static de.hhu.cs.dbs.dbwk.project.persistence.sql.sqlite.SQLHelper.executeQuery;

@Repository
public class BewertungRepoImpl implements BewertungRepo {
    @Override
    public List<Bewertung> getBewertungen(Connection conn) throws SQLException {
        String sql = "SELECT Gaertner_bewertet_Pflegemassnahme.Pflegemassnahme as pflegemassnahmeid," +
                " Gaertner_bewertet_Pflegemassnahme.ROWID as bewertungid, Gaertner.ROWID as gaertnerid" +
                " FROM Gaertner_bewertet_Pflegemassnahme" +
                " JOIN Gaertner ON Gaertner_bewertet_Pflegemassnahme.Email = Gaertner.Email;";
        HashMap<Integer, Object> hm = new HashMap<>();
        ResultSet resultSet = executeQuery(conn, hm, sql);
        ArrayList<Bewertung> bewertungen = new ArrayList<>();
        while (resultSet.next()) {
            Optional<Bewertung> bewertung = BewertungMapper(resultSet);
            bewertung.ifPresent(bewertungen::add);
        }
        return bewertungen;
    }

    private Optional<Bewertung> BewertungMapper(ResultSet resultSet) throws SQLException {
        return Optional.of(new Bewertung(resultSet.getInt("pflegemassnahmeid"),
                resultSet.getInt("bewertungid"),
                resultSet.getInt("gaertnerid")));


    }
}
